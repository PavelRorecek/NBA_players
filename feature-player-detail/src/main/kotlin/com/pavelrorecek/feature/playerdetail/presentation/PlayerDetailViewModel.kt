package com.pavelrorecek.feature.playerdetail.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class PlayerDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state: StateFlow<State> = _state

    data class State(
        val isLoadingVisible: Boolean = true,
    )
}
