package com.pavelrorecek.core.player.model

import com.pavelrorecek.core.player.model.Player.Team

public fun team(
    abbreviation: String = "LAL",
    city: String = "Los Angeles",
    conference: String = "West",
    division: String = "Pacific",
    fullName: String = "Los Angeles Lakers",
    name: String = "Lakers",
): Team = Team(
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    fullName = fullName,
    name = name,
)

public fun player(
    id: Player.Id = Player.Id(value = 0),
    firstName: String = "John",
    lastName: String = "Doe",
    position: String? = "F",
    heightFeet: Int = 5,
    heightInches: Int = 11,
    weightPounds: Int = 42,
    team: Team = team(),
): Player = Player(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    heightFeet = heightFeet,
    heightInches = heightInches,
    weightPounds = weightPounds,
    team = team,
)
