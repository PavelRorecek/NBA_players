package com.pavelrorecek.core.player.data

import com.pavelrorecek.core.player.model.Player
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.Test

internal class CurrentPlayerRepositoryImplTest {

    @Test
    fun `should load stored player`() {
        val player: Player = mockk()
        val repository = CurrentPlayerRepositoryImpl()

        repository.store(player)

        repository.load() shouldBe player
    }
}
