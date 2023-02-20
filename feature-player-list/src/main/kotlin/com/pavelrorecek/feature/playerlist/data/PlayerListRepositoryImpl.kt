package com.pavelrorecek.feature.playerlist.data

import com.pavelrorecek.core.player.data.PlayerApi
import com.pavelrorecek.core.player.data.PlayerListResponseDto
import com.pavelrorecek.core.player.data.PlayerListResponseDto.PlayerDto
import com.pavelrorecek.core.player.model.Player
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Loaded
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Loading
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Page
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

internal class PlayerListRepositoryImpl(
    private val api: PlayerApi,
) : PlayerListRepository {

    private val playerList = MutableStateFlow<PlayerList?>(null)

    override suspend fun requestFirstPage() {
        playerList.value = Loading(previousPages = emptyList())
        val response = api.loadPlayers(FIRST_PAGE, PER_PAGE)
        playerList.value = Loaded(pages = listOf(toDomain(response)))
    }

    private fun toDomain(dto: PlayerListResponseDto) = dto.data
        .mapNotNull { toDomain(it) }
        .let(::Page)

    @Suppress("ReturnCount")
    private fun toDomain(dto: PlayerDto): Player? {
        return Player(
            id = dto.id?.let(Player::Id) ?: return null,
            firstName = dto.firstName ?: return null,
            lastName = dto.lastName ?: return null,
            position = dto.position.orEmpty(),
            team = Player.Team(name = dto.team?.name ?: return null),
        )
    }

    override suspend fun requestNextPage() {
        val previousPages = (playerList.value as? Loaded)?.pages.orEmpty()
        playerList.value = Loading(previousPages = previousPages)
        val response = api.loadPlayers(page = previousPages.size, perPage = PER_PAGE)
        playerList.value = Loaded(pages = previousPages + listOf(toDomain(response)))
    }

    override fun observe(): Flow<PlayerList> = playerList.filterNotNull()

    private companion object {
        const val FIRST_PAGE = 0
        const val PER_PAGE = 35
    }
}
