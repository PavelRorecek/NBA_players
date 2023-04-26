package com.pavelrorecek.feature.playerdetail.presentation

import androidx.lifecycle.ViewModel
import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import com.pavelrorecek.feature.playerdetail.domain.PlayerDetailNavigationController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class PlayerDetailViewModel(
    strings: PlayerDetailStrings,
    loadPlayer: LoadCurrentPlayerUseCase,
    private val navigation: PlayerDetailNavigationController,
) : ViewModel() {

    private val _state: MutableStateFlow<State>
    val state: StateFlow<State>

    init {
        val player = loadPlayer()
        _state = MutableStateFlow(
            State(
                title = strings.title(
                    player.firstName,
                    player.lastName,
                ),
                firstName = strings.firstName(player.firstName),
                lastName = strings.lastName(player.lastName),
                position = strings.position(player.position.orEmpty()),
                isPositionVisible = !player.position.isNullOrEmpty(),
                height = strings.height(
                    heightFeet = player.heightFeet ?: 0,
                    heightInches = player.heightInches ?: 0,
                ),
                isHeightVisible = player.heightFeet != null && player.heightInches != null,
                weight = strings.weight(player.weightPounds ?: 0),
                isWeightVisible = player.weightPounds != null,
                team = strings.teamName(player.team?.name.orEmpty()),
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
