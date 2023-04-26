package com.pavelrorecek.feature.playerlist.domain

import com.pavelrorecek.core.player.model.Player
import kotlinx.coroutines.flow.Flow

internal interface PlayerListRepository {

    suspend fun requestFirstPage()
    suspend fun requestNextPage()
    fun observe(): Flow<PlayerList>

    sealed class PlayerList(open val pages: List<Page>) {

        data class Loading(override val pages: List<Page>) : PlayerList(pages)
        data class Loaded(override val pages: List<Page>) : PlayerList(pages)
        data class Failure(override val pages: List<Page>) : PlayerList(pages)

        data class Page(val playerList: List<Player>)
    }
}
