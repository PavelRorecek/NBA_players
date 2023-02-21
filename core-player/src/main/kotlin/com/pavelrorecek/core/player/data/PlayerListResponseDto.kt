package com.pavelrorecek.core.player.data

import com.google.gson.annotations.SerializedName

public data class PlayerListResponseDto(
    @SerializedName("data") val data: List<PlayerDto>,
) {
    public data class PlayerDto(
        @SerializedName("id") val id: Int?,
        @SerializedName("first_name") val firstName: String?,
        @SerializedName("last_name") val lastName: String?,
        @SerializedName("position") val position: String?,
        @SerializedName("height_feet") val heightFeet: Int?,
        @SerializedName("height_inches") val heightInches: Int?,
        @SerializedName("weight_pounds") val weightPounds: Int?,
        @SerializedName("team") val team: TeamDto?,
    )

    public data class TeamDto(
        @SerializedName("abbreviation") val abbreviation: String?,
        @SerializedName("city") val city: String?,
        @SerializedName("conference") val conference: String?,
        @SerializedName("division") val division: String?,
        @SerializedName("full_name") val fullName: String?,
        @SerializedName("name") val name: String?,
    )
}
