package com.pavelrorecek.feature.playerlist.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrorecek.core.network.platform.AppDispatchers
import com.pavelrorecek.core.player.domain.StoreCurrentPlayerUseCase
import com.pavelrorecek.core.player.model.Player
import com.pavelrorecek.feature.playerlist.R
import com.pavelrorecek.feature.playerlist.domain.ObservePlayerListUseCase
import com.pavelrorecek.feature.playerlist.domain.PlayerListNavigationController
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Failure
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
    private val dispatchers: AppDispatchers,
    private val requestFirstPage: RequestFirstPagePlayerListUseCase,
    private val requestNextPage: RequestNextPagePlayerListUseCase,
    private val observePlayerList: ObservePlayerListUseCase,
    private val storePlayer: StoreCurrentPlayerUseCase,
    private val navigation: PlayerListNavigationController,
) : ViewModel() {

    private val _state = MutableStateFlow(
        State(
            title = context.getString(R.string.player_list_title),
            errorMessage = context.getString(R.string.player_list_loading_error),
        ),
    )
    val state: StateFlow<State> = _state

    private var refreshJob: Job? = null
    private var nextPageRequestJob: Job? = null

    init {
        viewModelScope.launch(dispatchers.io) { requestFirstPage() }
        viewModelScope.launch(dispatchers.main) {
            observePlayerList().collect { result ->
                _state.value = _state.value.copy(
                    playerList = result.pages.flatMap { it.playerList }.map(::toState),
                    isLoadingVisible = result is Loading,
                    isErrorVisible = result is Failure,
                )
            }
        }
    }

    private fun toState(model: Player) = State.PlayerState(
        model = model,
        name = "${model.firstName} ${model.lastName}",
        position = context.getString(R.string.player_list_position, model.position),
        isPositionVisible = !model.position.isNullOrEmpty(),
        teamName = context.getString(R.string.player_list_team, model.team?.name),
        isTeamVisible = model.team != null,
    )

    fun onPlayer(player: Player) {
        storePlayer(player)
        navigation.goToPlayerDetail()
    }

    fun onRefresh() {
        if (refreshJob != null) return
        refreshJob = viewModelScope.launch(dispatchers.io) {
            requestFirstPage()
            refreshJob = null
        }
    }

    fun onEndReached() {
        if (nextPageRequestJob != null) return
        nextPageRequestJob = viewModelScope.launch(dispatchers.io) {
            val isAtLeastOnePageLoaded = observePlayerList().first().pages.isNotEmpty()
            if (isAtLeastOnePageLoaded) requestNextPage()

            nextPageRequestJob = null
        }
    }

    data class State(
        val title: String,
        val playerList: List<PlayerState> = emptyList(),
        val isLoadingVisible: Boolean = true,
        val isErrorVisible: Boolean = false,
        val errorMessage: String,
    ) {

        data class PlayerState(
            val model: Player,
            val name: String,
            val position: String,
            val isPositionVisible: Boolean,
            val isTeamVisible: Boolean,
            val teamName: String,
        )
    }
}
