package com.pavelrorecek.feature.teamdetail.platform

import android.content.Context
import com.pavelrorecek.feature.teamdetail.R
import com.pavelrorecek.feature.teamdetail.presentation.TeamDetailStrings

internal class TeamDetailStringsImpl(
    private val context: Context,
) : TeamDetailStrings {

    override fun title(name: String) = name
    override fun city(city: String) =
        context.getString(R.string.team_detail_city, city)

    override fun conference(conference: String) =
        context.getString(R.string.team_detail_conference, conference)

    override fun abbreviation(abbreviation: String) =
        context.getString(R.string.team_detail_abbreviation, abbreviation)

    override fun division(division: String) =
        context.getString(R.string.team_detail_division, division)

    override fun fullName(fullName: String) =
        context.getString(R.string.team_detail_full_name, fullName)

    override fun name(name: String) =
        context.getString(R.string.team_detail_name, name)
}
