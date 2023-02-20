package com.pavelrorecek.feature.playerlist.domain

internal class RequestFirstPagePlayerListUseCase(private val repository: PlayerListRepository) {

    suspend operator fun invoke() = repository.requestFirstPage()
}
