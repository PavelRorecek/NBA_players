package com.pavelrorecek.feature.playerlist.data

import app.cash.turbine.test
import com.pavelrorecek.core.player.data.PlayerApi
import com.pavelrorecek.core.player.data.PlayerListResponseDto
import com.pavelrorecek.core.player.model.Player
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Loaded
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Loading
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class PlayerListRepositoryImplTest {

    @Test
    fun `should request first page via api`() = runTest {
        val api: PlayerApi = mockk(relaxed = true)
        val repository = PlayerListRepositoryImpl(api = api)

        repository.requestFirstPage()

        coVerify { api.loadPlayers(page = 0, perPage = 35) }
    }

    @Test
    fun `should show loading when requesting first page`() = runTest {
        val api: PlayerApi = mockk(relaxed = true)
        val repository = PlayerListRepositoryImpl(api = api)

        repository.observe().test {
            repository.requestFirstPage()
            awaitItem() shouldBe Loading(emptyList())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should map players to domain`() = runTest {
        val api: PlayerApi = mockk(relaxed = true) {
            coEvery { loadPlayers(any(), any()) } returns PlayerListResponseDto(
                data = listOf(
                    PlayerListResponseDto.PlayerDto(
                        id = 42,
                        firstName = "LeBron",
                        lastName = "James",
                        position = "F",
                        team = PlayerListResponseDto.TeamDto(
                            name = "Lakers",
                        ),
                        heightFeet = 5,
                        heightInches = 11,
                        weightPounds = 42,
                    ),
                ),
            )
        }
        val repository = PlayerListRepositoryImpl(api = api)

        repository.requestFirstPage()

        repository.observe().first() shouldBe Loaded(
            pages = listOf(
                PlayerListRepository.PlayerList.Page(
                    playerList = listOf(
                        Player(
                            id = Player.Id(42),
                            firstName = "LeBron",
                            lastName = "James",
                            position = "F",
                            heightFeet = 5,
                            heightInches = 11,
                            weightPounds = 42,
                            team = Player.Team(
                                name = "Lakers",
                            ),
                        ),
                    ),
                ),
            ),
        )
    }

    @Test
    fun `should request next page via api`() = runTest {
        val api: PlayerApi = mockk(relaxed = true)
        val repository = PlayerListRepositoryImpl(api = api)

        repository.requestFirstPage()
        repository.requestNextPage()

        coVerify { api.loadPlayers(page = 1, perPage = 35) }
    }

    @Test
    fun `should show previous player list when loading next page`() = runTest {
        val api: PlayerApi = mockk(relaxed = true) {
            coEvery { loadPlayers(any(), any()) } returns PlayerListResponseDto(
                data = listOf(
                    PlayerListResponseDto.PlayerDto(
                        id = 42,
                        firstName = "LeBron",
                        lastName = "James",
                        position = "F",
                        team = PlayerListResponseDto.TeamDto(
                            name = "Lakers",
                        ),
                        heightFeet = 5,
                        heightInches = 11,
                        weightPounds = 42,
                    ),
                ),
            )
        }
        val repository = PlayerListRepositoryImpl(api = api)

        repository.requestFirstPage()

        repository.observe().test {
            awaitItem() // drop last loaded state
            repository.requestNextPage()
            awaitItem() shouldBe Loading(
                previousPages = listOf(
                    PlayerListRepository.PlayerList.Page(
                        playerList = listOf(
                            Player(
                                id = Player.Id(42),
                                firstName = "LeBron",
                                lastName = "James",
                                position = "F",
                                heightFeet = 5,
                                heightInches = 11,
                                weightPounds = 42,
                                team = Player.Team(
                                    name = "Lakers",
                                ),
                            ),
                        ),
                    ),
                ),
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}
