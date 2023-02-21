package com.pavelrorecek.feature.playerdetail.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import com.pavelrorecek.feature.playerdetail.PlayerDetailNavigationController
import com.pavelrorecek.feature.playerdetail.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class PlayerDetailViewModel(
    context: Context,
    loadPlayer: LoadCurrentPlayerUseCase,
    private val navigation: PlayerDetailNavigationController,
) : ViewModel() {

    private val _state: MutableStateFlow<State>
    val state: StateFlow<State>

    init {
        val player = loadPlayer()
        _state = MutableStateFlow(
            State(
                firstName = context.getString(R.string.player_detail_firstname, player.firstName),
                lastName = context.getString(R.string.player_detail_lastname, player.lastName),
                position = context.getString(R.string.player_detail_position, player.position),
                height = context.getString(
                    R.string.player_detail_height,
                    player.heightFeet,
                    player.heightInches,
                ),
                weight = context.getString(R.string.player_detail_weight, player.weightPounds),
                team = context.getString(R.string.player_detail_team, player.team.name),
            ),
        )
        state = _state
    }

    fun onTeam() {
        navigation.goToTeamDetail()
    }

    data class State(
        val firstName: String,
        val lastName: String,
        val position: String,
        val height: String,
        val weight: String,
        val team: String,
    )
}
