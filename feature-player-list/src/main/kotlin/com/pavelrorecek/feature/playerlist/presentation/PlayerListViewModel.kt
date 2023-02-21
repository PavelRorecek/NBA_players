package com.pavelrorecek.feature.playerlist.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrorecek.core.network.data.IoDispatcher
import com.pavelrorecek.core.player.domain.StoreCurrentPlayerUseCase
import com.pavelrorecek.core.player.model.Player
import com.pavelrorecek.feature.playerlist.R
import com.pavelrorecek.feature.playerlist.domain.ObservePlayerListUseCase
import com.pavelrorecek.feature.playerlist.domain.PlayerListNavigationController
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Failure
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Loaded
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Loading
import com.pavelrorecek.feature.playerlist.domain.RequestFirstPagePlayerListUseCase
import com.pavelrorecek.feature.playerlist.domain.RequestNextPagePlayerListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

internal class PlayerListViewModel(
    private val context: Context,
    private val ioDispatcher: IoDispatcher,
    private val requestFirstPage: RequestFirstPagePlayerListUseCase,
    private val requestNextPage: RequestNextPagePlayerListUseCase,
    private val observePlayerList: ObservePlayerListUseCase,
    private val storePlayer: StoreCurrentPlayerUseCase,
    private val navigation: PlayerListNavigationController,
) : ViewModel() {

    private val _state = MutableStateFlow(
        State(title = context.getString(R.string.player_list_title))
    )
    val state: StateFlow<State> = _state

    private var refreshJob: Job? = null
    private var nextPageRequestJob: Job? = null

    init {
        viewModelScope.launch(ioDispatcher.value) { requestFirstPage() }
        viewModelScope.launch {
            observePlayerList().collect { result ->
                val playerList = when (result) {
                    is Loading -> result.previousPages
                    is Loaded -> result.pages
                    is Failure -> emptyList()
                }.flatMap { it.playerList }

                _state.value = _state.value.copy(
                    playerList = playerList.map(::toState),
                    isLoadingVisible = result is Loading,
                )
            }
        }
    }

    private fun toState(model: Player) = State.PlayerState(
        model = model,
        name = "${model.firstName} ${model.lastName}",
        position = context.getString(R.string.player_list_position, model.position),
        teamName = context.getString(R.string.player_list_team, model.team.name),
    )

    fun onPlayer(player: Player) {
        storePlayer(player)
        navigation.goToPlayerDetail()
    }

    fun onRefresh() {
        if (refreshJob != null) return
        refreshJob = viewModelScope.launch(ioDispatcher.value) {
            requestFirstPage()
            refreshJob = null
        }
    }

    fun onEndReached() {
        if (nextPageRequestJob != null) return
        nextPageRequestJob = viewModelScope.launch(ioDispatcher.value) {
            val isAtLeastOnePageLoaded = (observePlayerList().first() as? Loaded)
                ?.pages
                .orEmpty()
                .isNotEmpty()
            if (isAtLeastOnePageLoaded) requestNextPage()

            nextPageRequestJob = null
        }
    }

    data class State(
        val title: String,
        val playerList: List<PlayerState> = emptyList(),
        val isLoadingVisible: Boolean = true,
    ) {

        data class PlayerState(
            val model: Player,
            val name: String,
            val position: String,
            val teamName: String,
        )
    }
}
