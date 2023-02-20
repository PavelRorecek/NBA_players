package com.pavelrorecek.core.player.domain

import com.pavelrorecek.core.player.model.Player

public class LoadCurrentPlayerUseCase internal constructor(
    private val repository: CurrentPlayerRepository,
) {

    public operator fun invoke(): Player = repository.load()
}
