package com.pavelrorecek.feature.playerlist.platform

import android.content.Context
import com.pavelrorecek.feature.playerlist.R
import com.pavelrorecek.feature.playerlist.presentation.PlayerListStrings

internal class PlayerListStringsImpl(
    private val context: Context,
) : PlayerListStrings {

    override fun title() = context.getString(R.string.player_list_title)
    override fun errorMessage() = context.getString(R.string.player_list_loading_error)
    override fun name(firstName: String, lastName: String) = "$firstName $lastName"
    override fun teamName(name: String) = context.getString(R.string.player_list_team, name)
    override fun position(position: String) =
        context.getString(R.string.player_list_position, position)
}
