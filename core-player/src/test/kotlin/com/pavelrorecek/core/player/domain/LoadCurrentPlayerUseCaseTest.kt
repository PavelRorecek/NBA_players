package com.pavelrorecek.core.player.domain

import com.pavelrorecek.core.player.model.Player
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

internal class LoadCurrentPlayerUseCaseTest {

    @Test
    fun `should load current player via repository`() {
        val player: Player = mockk()
        val repository: CurrentPlayerRepository = mockk {
            every { load() } returns player
        }
        val load = LoadCurrentPlayerUseCase(repository = repository)

        load() shouldBe player
    }
}
