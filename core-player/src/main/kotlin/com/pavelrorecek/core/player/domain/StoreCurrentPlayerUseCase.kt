package com.pavelrorecek.core.player.domain

import com.pavelrorecek.core.player.model.Player

public class StoreCurrentPlayerUseCase internal constructor(
    private val repository: CurrentPlayerRepository,
) {

    public operator fun invoke(player: Player) {
        repository.store(player)
    }
}
