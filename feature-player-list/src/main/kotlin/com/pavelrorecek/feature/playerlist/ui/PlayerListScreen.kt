package com.pavelrorecek.feature.playerlist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import com.pavelrorecek.feature.playerlist.presentation.PlayerListViewModel.State
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
    state: State,
    onRefresh: () -> Unit,
    onEndReached: () -> Unit,
    onPlayer: (Player) -> Unit,
) {
    BaseScreen {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(title = { Text(text = state.title) })
            },
            content = { padding ->
                val pullRefreshState = rememberPullRefreshState(false, onRefresh = onRefresh)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .pullRefresh(pullRefreshState),
                ) {
                    val listState = rememberLazyListState()

                    LazyColumn(state = listState, contentPadding = PaddingValues(vertical = 8.dp)) {
                        state.playerList.forEach { player ->
                            item(key = player.model.id.value) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 16.dp)
                                        .clickable(onClick = { onPlayer(player.model) }),
                                ) {
                                    Column(
                                        modifier = Modifier.padding(8.dp),
                                    ) {
                                        Text(text = player.name, fontWeight = Bold)
                                        if (player.isPositionVisible || player.isTeamVisible) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                        }
                                        if (player.isPositionVisible) Text(text = player.position)
                                        if (player.isTeamVisible) Text(text = player.teamName)
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
                        if (state.isErrorVisible) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(text = state.errorMessage)
                                }
                            }
                        }
                    }
                    if (!listState.canScrollForward) onEndReached()
                    PullRefreshIndicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        refreshing = false,
                        state = pullRefreshState,
                    )
                }
            },
        )
    }
}

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun PlayerListWithItems() {
    val playerList: List<State.PlayerState> = listOf(
        samplePlayerState(
            model = samplePlayerModel(0),
            name = "John Doe",
            position = "A",
            isPositionVisible = true,
            isTeamVisible = true,
            teamName = "Lakers",
        ),
        samplePlayerState(
            model = samplePlayerModel(1),
            name = "Jane Doe",
            isPositionVisible = false,
            isTeamVisible = false,
        ),
    )

    AppTheme {
        PlayerListScreen(
            state = sampleState(
                title = "NBA Players",
                playerList = playerList,
            ),
            onRefresh = {},
            onEndReached = {},
            onPlayer = {},
        )
    }
}

@Preview
@Composable
@Suppress("UnusedPrivateMember")
private fun PlayerListWithError() {
    val playerList: List<State.PlayerState> = listOf(
        samplePlayerState(
            model = samplePlayerModel(0),
            name = "John Doe",
            position = "A",
            isPositionVisible = true,
            isTeamVisible = true,
            teamName = "Lakers",
        ),
        samplePlayerState(
            model = samplePlayerModel(1),
            name = "Jane Doe",
            isPositionVisible = false,
            isTeamVisible = false,
        ),
    )

    AppTheme {
        PlayerListScreen(
            state = sampleState(
                title = "NBA Players",
                playerList = playerList,
                errorMessage = "Error while loading.",
                isErrorVisible = true,
            ),
            onRefresh = {},
            onEndReached = {},
            onPlayer = {},
        )
    }
}

private fun samplePlayerModel(id: Int) = Player(
    id = Player.Id(value = id),
    firstName = "Lupe Guy",
    lastName = "Christa Best",
    position = null,
    heightFeet = null,
    heightInches = null,
    weightPounds = null,
    team = null,
)

private fun samplePlayerState(
    model: Player,
    name: String,
    position: String = "",
    isPositionVisible: Boolean,
    isTeamVisible: Boolean,
    teamName: String = "",
) = State.PlayerState(
    model = model,
    name = name,
    position = position,
    isPositionVisible = isPositionVisible,
    isTeamVisible = isTeamVisible,
    teamName = teamName,
)

private fun sampleState(
    title: String,
    playerList: List<State.PlayerState> = emptyList(),
    isLoadingVisible: Boolean = false,
    isErrorVisible: Boolean = false,
    errorMessage: String = "Error",
) = State(
    title = title,
    playerList = playerList,
    isLoadingVisible = isLoadingVisible,
    isErrorVisible = isErrorVisible,
    errorMessage = errorMessage,
)
