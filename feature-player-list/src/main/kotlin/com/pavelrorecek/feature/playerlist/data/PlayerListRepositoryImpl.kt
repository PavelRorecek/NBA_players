package com.pavelrorecek.feature.playerlist.data

import com.pavelrorecek.core.player.data.PlayerApi
import com.pavelrorecek.core.player.data.PlayerListResponseDto
import com.pavelrorecek.core.player.data.PlayerListResponseDto.PlayerDto
import com.pavelrorecek.core.player.model.Player
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository.PlayerList.Failure
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
        playerList.value = Loading(pages = emptyList())
        val response = runCatching { api.loadPlayers(FIRST_PAGE, PER_PAGE) }.getOrNull()

        playerList.value = if (response != null) {
            Loaded(pages = listOf(toDomain(response)))
        } else {
            Failure(emptyList())
        }
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
            position = dto.position,
            heightFeet = dto.heightFeet,
            heightInches = dto.heightInches,
            weightPounds = dto.weightPounds,
            team = dto.team?.let { teamDto ->
                Player.Team(
                    name = teamDto.name,
                    fullName = teamDto.fullName,
                    abbreviation = teamDto.abbreviation,
                    city = teamDto.city,
                    conference = teamDto.conference,
                    division = teamDto.division,
                )
            },
        )
    }

    override suspend fun requestNextPage() {
        val previousPages = playerList.value?.pages.orEmpty()
        playerList.value = Loading(pages = previousPages)

        val response = runCatching {
            api.loadPlayers(
                page = previousPages.size,
                perPage = PER_PAGE,
            )
        }.getOrNull()

        playerList.value = if (response != null) {
            Loaded(pages = previousPages + listOf(toDomain(response)))
        } else {
            Failure(pages = previousPages)
        }
    }

    override fun observe(): Flow<PlayerList> = playerList.filterNotNull()

    private companion object {
        const val FIRST_PAGE = 0
        const val PER_PAGE = 35
    }
}
