package com.pavelrorecek.feature.playerdetail.presentation

internal interface PlayerDetailStrings {
    fun title(firstName: String, lastName: String): String
    fun firstName(name: String): String
    fun lastName(name: String): String
    fun position(position: String): String
    fun height(heightFeet: Int, heightInches: Int): String
    fun weight(weightPounds: Int): String
    fun teamName(name: String): String
}
