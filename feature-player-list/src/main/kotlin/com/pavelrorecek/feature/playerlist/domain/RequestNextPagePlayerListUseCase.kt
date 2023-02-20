package com.pavelrorecek.feature.playerlist.domain

internal class RequestNextPagePlayerListUseCase(private val repository: PlayerListRepository) {

    suspend operator fun invoke() = repository.requestNextPage()
}
