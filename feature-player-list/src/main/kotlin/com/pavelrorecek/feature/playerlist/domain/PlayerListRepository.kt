package com.pavelrorecek.feature.playerlist.domain

import com.pavelrorecek.core.player.model.Player
import kotlinx.coroutines.flow.Flow

internal interface PlayerListRepository {

    suspend fun requestFirstPage()
    suspend fun requestNextPage()
    fun observe(): Flow<PlayerList>

    sealed class PlayerList {

        data class Loading(val previousPages: List<Page>) : PlayerList()
        data class Loaded(val pages: List<Page>) : PlayerList()
        object Failure : PlayerList()

        data class Page(val playerList: List<Player>)
    }
}
