package com.pavelrorecek.feature.teamdetail.presentation

import android.content.Context
import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import com.pavelrorecek.core.player.model.player
import com.pavelrorecek.core.player.model.team
import com.pavelrorecek.feature.playerdetail.R
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

internal class TeamDetailViewModelTest {

    @Test
    fun `should map team to state`() {
        val context: Context = mockk {
            every {
                getString(R.string.team_detail_city, "Los Angeles")
            } returns "City: Los Angeles"
            every {
                getString(R.string.team_detail_conference, "West")
            } returns "Conference: West"
            every {
                getString(R.string.team_detail_abbreviation, "LAL")
            } returns "Abbreviation: LAL"
            every {
                getString(R.string.team_detail_division, "Pacific")
            } returns "Division: Pacific"
            every {
                getString(R.string.team_detail_full_name, "Los Angeles Lakers")
            } returns "Full name: Los Angeles Lakers"
            every {
                getString(R.string.team_detail_name, "Lakers")
            } returns "Name: Lakers"
        }
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(
                team = team(
                    abbreviation = "LAL",
                    city = "Los Angeles",
                    conference = "West",
                    division = "Pacific",
                    fullName = "Los Angeles Lakers",
                    name = "Lakers",
                ),
            )
        }
        val viewModel = TeamDetailViewModel(
            context = context,
            loadPlayer = loadPlayer,
        )

        viewModel.state.value.run {
            city shouldBe "City: Los Angeles"
            conference shouldBe "Conference: West"
            abbreviation shouldBe "Abbreviation: LAL"
            division shouldBe "Division: Pacific"
            fullName shouldBe "Full name: Los Angeles Lakers"
            name shouldBe "Name: Lakers"
        }
    }
}
