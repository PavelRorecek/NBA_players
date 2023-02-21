package com.pavelrorecek.feature.teamdetail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pavelrorecek.core.design.BaseScreen
import com.pavelrorecek.feature.teamdetail.presentation.TeamDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
public fun TeamDetailScreen() {
    val viewModel: TeamDetailViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value

    TeamDetailScreen(state = state)
}

@Composable
internal fun TeamDetailScreen(
    state: TeamDetailViewModel.State,
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
                    Text(text = state.name)
                    Text(text = state.fullName)
                    Text(text = state.abbreviation)
                    Text(text = state.city)
                    Text(text = state.conference)
                    Text(text = state.division)
                }
            },
        )
    }
}
