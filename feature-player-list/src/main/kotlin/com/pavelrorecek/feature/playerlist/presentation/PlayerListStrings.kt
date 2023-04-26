package com.pavelrorecek.feature.playerlist.presentation

internal interface PlayerListStrings {

    fun title(): String
    fun errorMessage(): String
    fun name(firstName: String, lastName: String): String
    fun position(position: String): String
    fun teamName(name: String): String
}
