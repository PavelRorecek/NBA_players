package com.pavelrorecek.core.player.model

public data class Player(
    val id: Id,
    val firstName: String,
    val lastName: String,
    val position: String?,
    val heightFeet: Int?,
    val heightInches: Int?,
    val weightPounds: Int?,
    val team: Team?,
) {
    @JvmInline
    public value class Id(public val value: Int)

    public data class Team(
        val name: String?,
        val fullName: String?,
        val abbreviation: String?,
        val city: String?,
        val conference: String?,
        val division: String?,
    )
}
