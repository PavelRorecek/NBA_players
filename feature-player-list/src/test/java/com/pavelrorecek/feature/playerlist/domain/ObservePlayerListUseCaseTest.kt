package com.pavelrorecek.feature.playerlist.domain

import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class ObservePlayerListUseCaseTest {

    @Test
    fun `should observe player list via repository`() = runTest {
        val playerList: PlayerList = mockk()
        val repository: PlayerListRepository = mockk {
            every { observe() } returns flowOf(playerList)
        }
        val observe = ObservePlayerListUseCase(repository = repository)

        observe().first() shouldBe playerList
    }
}
