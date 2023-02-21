package com.pavelrorecek.feature.teamdetail.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import com.pavelrorecek.feature.playerdetail.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class TeamDetailViewModel(
    context: Context,
    loadPlayer: LoadCurrentPlayerUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<State>
    val state: StateFlow<State>

    init {
        val team = loadPlayer().team
        _state = MutableStateFlow(
            State(
                title = team.name.orEmpty(),
                city = context.getString(R.string.team_detail_city, team.city),
                conference = context.getString(R.string.team_detail_conference, team.conference),
                abbreviation = context.getString(
                    R.string.team_detail_abbreviation,
                    team.abbreviation,
                ),
                division = context.getString(R.string.team_detail_division, team.division),
                fullName = context.getString(R.string.team_detail_full_name, team.fullName),
                name = context.getString(R.string.team_detail_name, team.name),
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
