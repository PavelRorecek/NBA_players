package com.pavelrorecek.core.player.domain

import com.pavelrorecek.core.player.model.Player
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

internal class StoreCurrentPlayerUseCaseTest {

    @Test
    fun `should store current player via repository`() {
        val player: Player = mockk()
        val repository: CurrentPlayerRepository = mockk(relaxUnitFun = true)
        val store = StoreCurrentPlayerUseCase(repository = repository)

        store(player)

        verify { repository.store(player) }
    }
}
