package com.pavelrorecek.core.player.data

import retrofit2.http.GET
import retrofit2.http.Query

public interface PlayerApi {

    @GET("players")
    public suspend fun loadPlayers(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): PlayerListResponseDto
}
