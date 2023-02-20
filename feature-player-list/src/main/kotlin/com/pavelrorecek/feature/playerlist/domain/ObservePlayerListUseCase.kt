package com.pavelrorecek.feature.playerlist.domain

import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList
import kotlinx.coroutines.flow.Flow

internal class ObservePlayerListUseCase(private val repository: PlayerListRepository) {

    operator fun invoke(): Flow<PlayerList> = repository.observe()
}
