package com.pavelrorecek.core.player.data

import com.google.gson.annotations.SerializedName

public data class PlayerListResponseDto(
    val data: List<PlayerDto>,
) {
    public data class PlayerDto(
        val id: Int?,
        @SerializedName("first_name") val firstName: String?,
        @SerializedName("last_name") val lastName: String?,
        val position: String?,
        val team: TeamDto?,
    )

    public data class TeamDto(val name: String?)
}
