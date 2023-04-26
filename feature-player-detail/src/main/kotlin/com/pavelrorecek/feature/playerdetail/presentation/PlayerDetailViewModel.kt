package com.pavelrorecek.feature.playerdetail.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import com.pavelrorecek.feature.playerdetail.R
import com.pavelrorecek.feature.playerdetail.domain.PlayerDetailNavigationController
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
                title = context.getString(
                    R.string.player_detail_fullname,
                    player.firstName,
                    player.lastName,
                ),
                firstName = context.getString(R.string.player_detail_firstname, player.firstName),
                lastName = context.getString(R.string.player_detail_lastname, player.lastName),
                position = context.getString(R.string.player_detail_position, player.position),
                isPositionVisible = !player.position.isNullOrEmpty(),
                height = context.getString(
                    R.string.player_detail_height,
                    player.heightFeet,
                    player.heightInches,
                ),
                isHeightVisible = player.heightFeet != null && player.heightInches != null,
                weight = context.getString(R.string.player_detail_weight, player.weightPounds),
                isWeightVisible = player.weightPounds != null,
                team = context.getString(R.string.player_detail_team, player.team?.name.orEmpty()),
                isTeamVisible = player.team != null,
            ),
        )
        state = _state
    }

    fun onTeam() {
        navigation.goToTeamDetail()
    }

    data class State(
        val title: String,
        val firstName: String,
        val lastName: String,
        val position: String,
        val isPositionVisible: Boolean,
        val height: String,
        val isHeightVisible: Boolean,
        val weight: String,
        val isWeightVisible: Boolean,
        val team: String,
        val isTeamVisible: Boolean,
    )
}
