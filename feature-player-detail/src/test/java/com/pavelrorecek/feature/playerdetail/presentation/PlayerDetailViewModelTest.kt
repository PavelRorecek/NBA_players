package com.pavelrorecek.feature.playerdetail.presentation

import android.content.Context
import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import com.pavelrorecek.core.player.model.player
import com.pavelrorecek.core.player.model.team
import com.pavelrorecek.feature.playerdetail.R
import com.pavelrorecek.feature.playerdetail.domain.PlayerDetailNavigationController
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

internal class PlayerDetailViewModelTest {

    @Test
    fun `should map player name to title`() {
        val context: Context = mockk {
            every { getString(any(), any()) } returns ""
            every { getString(any(), any(), any()) } returns ""
            every { getString(R.string.player_detail_fullname, "John", "Doe") } returns "John Doe"
        }
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(
                firstName = "John",
                lastName = "Doe",
            )
        }
        val viewModel = viewModel(
            context = context,
            loadPlayer = loadPlayer,
        )

        viewModel.state.value.title shouldBe "John Doe"
    }

    @Test
    fun `should map player to state`() {
        val context: Context = mockk {
            every { getString(any(), any(), any()) } returns ""
            every { getString(R.string.player_detail_firstname, "John") } returns "Firstname: John"
            every { getString(R.string.player_detail_lastname, "Doe") } returns "Lastname: Doe"
            every { getString(R.string.player_detail_position, "F") } returns "Position: F"
            every {
                getString(R.string.player_detail_height, 5, 11)
            } returns "Height: 5 feet 11 inches"
            every { getString(R.string.player_detail_weight, 42) } returns "Weight: 42"
            every { getString(R.string.player_detail_team, "Lakers") } returns "Team: Lakers"
        }
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(
                firstName = "John",
                lastName = "Doe",
                position = "F",
                heightFeet = 5,
                heightInches = 11,
                weightPounds = 42,
                team = team(name = "Lakers"),
            )
        }
        val viewModel = viewModel(
            context = context,
            loadPlayer = loadPlayer,
        )

        viewModel.state.value.run {
            firstName shouldBe "Firstname: John"
            lastName shouldBe "Lastname: Doe"
            position shouldBe "Position: F"
            height shouldBe "Height: 5 feet 11 inches"
            weight shouldBe "Weight: 42"
            team shouldBe "Team: Lakers"
        }
    }

    @Test
    fun `should navigate to team detail`() {
        val navigation: PlayerDetailNavigationController = mockk(relaxUnitFun = true)
        val viewModel = viewModel(navigation = navigation)

        viewModel.onTeam()

        verify { navigation.goToTeamDetail() }
    }

    private fun viewModel(
        context: Context = mockk {
            every { getString(any(), any()) } returns ""
            every { getString(any(), any(), any()) } returns ""
        },
        loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player()
        },
        navigation: PlayerDetailNavigationController = mockk(relaxUnitFun = true),
    ) = PlayerDetailViewModel(
        context = context,
        loadPlayer = loadPlayer,
        navigation = navigation,
    )
}
