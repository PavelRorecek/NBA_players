package com.pavelrorecek.feature.playerdetail.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pavelrorecek.core.design.BaseScreen
import com.pavelrorecek.feature.playerdetail.presentation.PlayerDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
public fun PlayerDetailScreen() {
    val viewModel: PlayerDetailViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value

    PlayerDetailScreen(
        state = state,
        onTeam = viewModel::onTeam,
    )
}

@Composable
internal fun PlayerDetailScreen(
    state: PlayerDetailViewModel.State,
    onTeam: () -> Unit,
) {
    BaseScreen {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = state.title) })
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(text = state.firstName)
                    Text(text = state.lastName)
                    if (state.isPositionVisible) Text(text = state.position)
                    if (state.isHeightVisible) Text(text = state.height)
                    if (state.isWeightVisible) Text(text = state.weight)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = onTeam),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(text = state.team)
                        }
                    }
                }
            },
        )
    }
}
