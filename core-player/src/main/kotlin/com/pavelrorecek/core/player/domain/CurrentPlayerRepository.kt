package com.pavelrorecek.core.player.domain

import com.pavelrorecek.core.player.model.Player

internal interface CurrentPlayerRepository {

    fun store(player: Player)
    fun load(): Player
}
