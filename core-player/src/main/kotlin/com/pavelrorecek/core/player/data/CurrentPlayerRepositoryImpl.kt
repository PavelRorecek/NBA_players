package com.pavelrorecek.core.player.data

import com.pavelrorecek.core.player.domain.CurrentPlayerRepository
import com.pavelrorecek.core.player.model.Player
import kotlinx.coroutines.flow.MutableStateFlow

internal class CurrentPlayerRepositoryImpl : CurrentPlayerRepository {

    private val player = MutableStateFlow<Player?>(null)

    override fun store(player: Player) {
        this.player.value = player
    }

    override fun load(): Player = checkNotNull(player.value)
}
