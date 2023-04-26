package com.pavelrorecek.feature.playerlist.presentation

import android.content.Context
import com.pavelrorecek.core.network.platform.AppDispatchers
import com.pavelrorecek.core.player.domain.StoreCurrentPlayerUseCase
import com.pavelrorecek.core.player.model.Player
import com.pavelrorecek.core.player.model.player
import com.pavelrorecek.core.player.model.team
import com.pavelrorecek.core.test.TestDispatcherRule
import com.pavelrorecek.feature.playerlist.R
import com.pavelrorecek.feature.playerlist.domain.ObservePlayerListUseCase
import com.pavelrorecek.feature.playerlist.domain.PlayerListNavigationController
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
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

internal class PlayerListViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    @Test
    fun `should map title to state`() = runTest {
        val viewModel = viewModel(
            context = mockk {
                every { getString(R.string.player_list_title) } returns "Title"
            },
        )

        viewModel.state.value.title shouldBe "Title"
    }

    @Test
    fun `should request first page initially`() = runTest {
        val request: RequestFirstPagePlayerListUseCase = mockk()

        viewModel(requestFirstPage = request)

        coVerify { request() }
    }

    @Test
    fun `should request first page on refresh`() = runTest {
        val request: RequestFirstPagePlayerListUseCase = mockk()
        val viewModel = viewModel(requestFirstPage = request)

        clearMocks(request, answers = false)
        viewModel.onRefresh()

        coVerify { request() }
    }

    @Test
    fun `should map loaded player list to state`() = runTest {
        val player = player(
            id = Player.Id(value = 42),
            firstName = "John",
            lastName = "Doe",
            position = "F",
            team = team(name = "Lakers"),
        )
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(
                Loaded(pages = listOf(Page(playerList = listOf(player)))),
            )
        }
        val viewModel = viewModel(
            context = mockk {
                every { getString(any()) } returns ""
                every { getString(R.string.player_list_position, "F") } returns "Position: F"
                every { getString(R.string.player_list_team, "Lakers") } returns "Team: Lakers"
            },
            observePlayerList = observePlayerList,
        )

        viewModel.state.value.playerList.single().run {
            model shouldBe player
            name shouldBe "John Doe"
            position shouldBe "Position: F"
            teamName shouldBe "Team: Lakers"
        }
    }

    @Test
    fun `should show player position when it is not missing`() = runTest {
        val player = player(position = "F")
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(
                Loaded(pages = listOf(Page(playerList = listOf(player)))),
            )
        }
        val viewModel = viewModel(observePlayerList = observePlayerList)

        viewModel.state.value.playerList.single().isPositionVisible shouldBe true
    }

    @Test
    fun `should hide player position when it is missing`() = runTest {
        val player = player(position = "")
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(
                Loaded(pages = listOf(Page(playerList = listOf(player)))),
            )
        }
        val viewModel = viewModel(observePlayerList = observePlayerList)

        viewModel.state.value.playerList.single().isPositionVisible shouldBe false
    }

    @Test
    fun `should show player team when it is not missing`() = runTest {
        val player = player(team = team())
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(
                Loaded(pages = listOf(Page(playerList = listOf(player)))),
            )
        }
        val viewModel = viewModel(observePlayerList = observePlayerList)

        viewModel.state.value.playerList.single().isTeamVisible shouldBe true
    }

    @Test
    fun `should hide player team when it is missing`() = runTest {
        val player = player(team = null)
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(
                Loaded(pages = listOf(Page(playerList = listOf(player)))),
            )
        }
        val viewModel = viewModel(observePlayerList = observePlayerList)

        viewModel.state.value.playerList.single().isTeamVisible shouldBe false
    }

    @Test
    fun `should show loading when loading new list`() = runTest {
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(Loading(previousPages = emptyList()))
        }
        val viewModel = viewModel(observePlayerList = observePlayerList)

        viewModel.state.value.isLoadingVisible shouldBe true
    }

    @Test
    fun `should not show loading when list is loaded`() = runTest {
        val observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(Loaded(pages = emptyList()))
        }
        val viewModel = viewModel(observePlayerList = observePlayerList)

        viewModel.state.value.isLoadingVisible shouldBe false
    }

    @Test
    fun `should request next page when list end is reached`() = runTest {
        val request: RequestNextPagePlayerListUseCase = mockk()
        val viewModel = viewModel(
            requestNextPage = request,
            observePlayerList = mockk {
                every { this@mockk.invoke() } returns flowOf(
                    Loaded(
                        pages = listOf(
                            Page(
                                playerList = listOf(
                                    player(
                                        id = Player.Id(value = 42),
                                        firstName = "John",
                                        lastName = "Doe",
                                        position = "F",
                                        team = team(name = "Lakers"),
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

    @Test
    fun `should store player on navigate`() = runTest {
        val player: Player = mockk()
        val storePlayer: StoreCurrentPlayerUseCase = mockk(relaxed = true)
        val viewModel = viewModel(storePlayer = storePlayer)

        viewModel.onPlayer(player)

        verify { storePlayer(player) }
    }

    @Test
    fun `should navigate`() = runTest {
        val navigation: PlayerListNavigationController = mockk(relaxUnitFun = true)
        val viewModel = viewModel(navigation = navigation)

        viewModel.onPlayer(mockk())

        verify { navigation.goToPlayerDetail() }
    }

    private fun viewModel(
        context: Context = mockk {
            every { getString(any()) } returns ""
            every { getString(any(), any()) } returns ""
        },
        requestFirstPage: RequestFirstPagePlayerListUseCase = mockk(relaxUnitFun = true),
        requestNextPage: RequestNextPagePlayerListUseCase = mockk(relaxUnitFun = true),
        observePlayerList: ObservePlayerListUseCase = mockk {
            every { this@mockk.invoke() } returns flowOf(Loaded(emptyList()))
        },
        storePlayer: StoreCurrentPlayerUseCase = mockk(relaxed = true),
        navigation: PlayerListNavigationController = mockk(relaxUnitFun = true),
    ) = PlayerListViewModel(
        context = context,
        dispatchers = AppDispatchers(
            main = testDispatcherRule.testDispatcher,
            io = testDispatcherRule.testDispatcher,
        ),
        requestFirstPage = requestFirstPage,
        requestNextPage = requestNextPage,
        observePlayerList = observePlayerList,
        storePlayer = storePlayer,
        navigation = navigation,
    )
}
