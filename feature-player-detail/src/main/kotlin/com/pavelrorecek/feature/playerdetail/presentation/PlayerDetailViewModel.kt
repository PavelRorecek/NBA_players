package com.pavelrorecek.feature.playerdetail.presentation

import androidx.lifecycle.ViewModel
import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class PlayerDetailViewModel(
    loadPlayer: LoadCurrentPlayerUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        State(message = loadPlayer().toString()),
    )
    val state: StateFlow<State> = _state

    data class State(
        val message: String,
    )
}
