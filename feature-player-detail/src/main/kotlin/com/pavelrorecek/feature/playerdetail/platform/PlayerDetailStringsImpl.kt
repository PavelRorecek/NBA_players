package com.pavelrorecek.feature.playerdetail.platform

import android.content.Context
import com.pavelrorecek.feature.playerdetail.R
import com.pavelrorecek.feature.playerdetail.presentation.PlayerDetailStrings

internal class PlayerDetailStringsImpl(
    private val context: Context,
) : PlayerDetailStrings {

    override fun title(firstName: String, lastName: String) =
        context.getString(R.string.player_detail_fullname, firstName, lastName)

    override fun firstName(name: String) = context.getString(R.string.player_detail_firstname, name)
    override fun lastName(name: String) = context.getString(R.string.player_detail_lastname, name)
    override fun position(position: String) =
        context.getString(R.string.player_detail_position, position)

    override fun height(heightFeet: Int, heightInches: Int) =
        context.getString(R.string.player_detail_height, heightFeet, heightInches)

    override fun weight(weightPounds: Int) =
        context.getString(R.string.player_detail_weight, weightPounds)

    override fun teamName(name: String) = context.getString(R.string.player_detail_team, name)
}
