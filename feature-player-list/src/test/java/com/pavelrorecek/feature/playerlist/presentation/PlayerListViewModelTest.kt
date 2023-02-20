package com.pavelrorecek.feature.playerlist.presentation

import android.content.Context
import app.cash.turbine.test
import com.pavelrorecek.core.network.data.IoDispatcher
import com.pavelrorecek.core.player.model.Player
import com.pavelrorecek.core.test.TestDispatcherRule
import com.pavelrorecek.feature.playerlist.R
import com.pavelrorecek.feature.playerlist.domain.ObservePlayerListUseCase
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Loaded
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Loading
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Page
import com.pavelrorecek.feature.playerlist.domain.RequestFirstPagePlayerListUseCase
import com.pavelrorecek.feature.playerlist.domain.RequestNextPagePlayerListUseCase
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.CoroutineContext

internal class PlayerListViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    @Test
    fun `should request first page initially`() = runTest {
        val request: RequestFirstPagePlayerListUseCase = mockk()

        viewModel(
            ioDispatcher = testDispatcherRule.testDispatcher,
            requestFirstPage = request,
        )

        coVerify { request() }
    }

    @Test
    fun `should request first page on refresh`() = runTest {
        val request: RequestFirstPagePlayerListUseCase = mockk()
        val viewModel = viewModel(
            ioDispatcher = testDispatcherRule.testDispatcher,
            requestFirstPage = request,
        )

        clearMocks(request, answers = false)
        viewModel.onRefresh()

        coVerify { request() }
    }

    @Test
    fun `should map loaded player list to state`() = runTest {
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(
                Loaded(
                    pages = listOf(
                        Page(
                            playerList = listOf(
                                Player(
                                    id = Player.Id(value = 42),
                                    firstName = "John",
                                    lastName = "Doe",
                                    position = "F",
                                    team = Player.Team(name = "Lakers"),
                                ),
                            ),
                        ),
                    ),
                ),
            )
        }
        val viewModel = viewModel(
            context = mockk {
                every { getString(R.string.player_list_position, "F") } returns "Position: F"
                every { getString(R.string.player_list_team, "Lakers") } returns "Team: Lakers"
            },
            ioDispatcher = testDispatcherRule.testDispatcher,
            observePlayerList = observePlayerList,
        )

        viewModel.state.test {
            awaitItem().playerList.single() shouldBe PlayerListViewModel.State.PlayerState(
                id = Player.Id(value = 42),
                name = "John Doe",
                position = "Position: F",
                teamName = "Team: Lakers",
            )
        }
    }

    @Test
    fun `should show loading when loading new list`() = runTest {
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(Loading(previousPages = emptyList()))
        }
        val viewModel = viewModel(
            ioDispatcher = testDispatcherRule.testDispatcher,
            observePlayerList = observePlayerList,
        )

        viewModel.state.test {
            awaitItem().isLoadingVisible shouldBe true
        }
    }

    @Test
    fun `should not show loading when list is loaded`() = runTest {
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(Loaded(pages = emptyList()))
        }
        val viewModel = viewModel(
            ioDispatcher = testDispatcherRule.testDispatcher,
            observePlayerList = observePlayerList,
        )

        viewModel.state.test {
            awaitItem().isLoadingVisible shouldBe false
        }
    }

    @Test
    fun `should request next page when list end is reached`() = runTest {
        val request: RequestNextPagePlayerListUseCase = mockk()
        val viewModel = viewModel(
            ioDispatcher = testDispatcherRule.testDispatcher,
            requestNextPage = request,
            observePlayerList = mockk {
                every { this@mockk.invoke() } returns flowOf(
                    Loaded(
                        pages = listOf(
                            Page(
                                playerList = listOf(
                                    Player(
                                        id = Player.Id(value = 42),
                                        firstName = "John",
                                        lastName = "Doe",
                                        position = "F",
                                        team = Player.Team(
                                            name = "Lakers",
                                        ),
                                    ),
                                ),
                            ),
                        ),
                    ),
                )
            },
        )

        viewModel.onEndReached()

        coVerify { request() }
    }

    private fun viewModel(
        context: Context = mockk { every { getString(any(), any()) } returns "" },
        ioDispatcher: CoroutineContext,
        requestFirstPage: RequestFirstPagePlayerListUseCase = mockk(relaxUnitFun = true),
        requestNextPage: RequestNextPagePlayerListUseCase = mockk(relaxUnitFun = true),
        observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(Loaded(emptyList()))
        },
    ) = PlayerListViewModel(
        context = context,
        ioDispatcher = IoDispatcher(ioDispatcher),
        requestFirstPage = requestFirstPage,
        requestNextPage = requestNextPage,
        observePlayerList = observePlayerList,
    )
}
