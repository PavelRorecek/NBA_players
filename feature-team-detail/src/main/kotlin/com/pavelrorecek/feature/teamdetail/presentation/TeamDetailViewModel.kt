package com.pavelrorecek.feature.teamdetail.presentation

import androidx.lifecycle.ViewModel
import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class TeamDetailViewModel(
    strings: TeamDetailStrings,
    loadPlayer: LoadCurrentPlayerUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<State>
    val state: StateFlow<State>

    init {
        val team = checkNotNull(loadPlayer().team)
        _state = MutableStateFlow(
            State(
                title = strings.title(team.name.orEmpty()),
                city = strings.city(team.city.orEmpty()),
                conference = strings.conference(team.conference.orEmpty()),
                abbreviation = strings.abbreviation(team.abbreviation.orEmpty()),
                division = strings.division(team.division.orEmpty()),
                fullName = strings.fullName(team.fullName.orEmpty()),
                name = strings.name(team.name.orEmpty()),
            ),
        )
        state = _state
    }

    data class State(
        val title: String,
        val abbreviation: String,
        val city: String,
        val conference: String,
        val division: String,
        val fullName: String,
        val name: String,
    )
}
