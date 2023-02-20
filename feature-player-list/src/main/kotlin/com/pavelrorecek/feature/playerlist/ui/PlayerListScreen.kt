package com.pavelrorecek.feature.playerlist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pavelrorecek.core.design.AppTheme
import com.pavelrorecek.core.design.BaseScreen
import com.pavelrorecek.core.player.model.Player
import com.pavelrorecek.feature.playerlist.presentation.PlayerListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
public fun PlayerListScreen() {
    val viewModel: PlayerListViewModel = koinViewModel()
    val state = viewModel.state.collectAsState().value

    PlayerListScreen(
        state = state,
        onRefresh = viewModel::onRefresh,
        onEndReached = viewModel::onEndReached,
        onPlayer = viewModel::onPlayer,
    )
}

@Composable
internal fun PlayerListScreen(
    state: PlayerListViewModel.State,
    onRefresh: () -> Unit,
    onEndReached: () -> Unit,
    onPlayer: (Player) -> Unit,
) {
    BaseScreen {
        val pullRefreshState = rememberPullRefreshState(false, onRefresh = onRefresh)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
        ) {
            val listState = rememberLazyListState()

            LazyColumn(state = listState) {
                state.playerList.forEach {
                    item(key = it.model.id.value) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                                .clickable(onClick = { onPlayer(it.model) }),
                        ) {
                            Column(
                                modifier = Modifier.padding(8.dp),
                            ) {
                                Text(text = it.name, fontWeight = Bold)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = it.position)
                                Text(text = it.teamName)
                            }
                        }
                    }
                }
                if (state.isLoadingVisible) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
            if (!listState.canScrollForward) onEndReached()
            PullRefreshIndicator(false, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun PlayerListWithItems() {
    AppTheme {
        PlayerListScreen(
            state = PlayerListViewModel.State(),
            onRefresh = {},
            onEndReached = {},
            onPlayer = {},
        )
    }
}
