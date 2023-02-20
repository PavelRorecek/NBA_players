package com.pavelrorecek.feature.playerdetail.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.pavelrorecek.core.design.BaseScreen
import com.pavelrorecek.feature.playerdetail.presentation.PlayerDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
public fun PlayerDetailScreen() {
    val viewModel: PlayerDetailViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value

    PlayerDetailScreen(
        state = state,
    )
}

@Composable
internal fun PlayerDetailScreen(
    state: PlayerDetailViewModel.State,
) {
    BaseScreen {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = state.message)
        }
    }
}
