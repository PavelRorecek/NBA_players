package com.pavelrorecek.feature.playerdetail.presentation

import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import com.pavelrorecek.core.player.model.player
import com.pavelrorecek.core.player.model.team
import com.pavelrorecek.feature.playerdetail.domain.PlayerDetailNavigationController
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

internal class PlayerDetailViewModelTest {

    @Test
    fun `should map player name to title`() {
        val viewModel = viewModel(
            strings = mockk(relaxed = true) { every { title("John", "Doe") } returns "Title" },
            loadPlayer = mockk {
                every { this@mockk.invoke() } returns player(
                    firstName = "John",
                    lastName = "Doe",
                )
            },
        )

        viewModel.state.value.title shouldBe "Title"
    }

    @Test
    fun `should map player to state`() {
        val strings: PlayerDetailStrings = mockk(relaxed = true) {
            every { firstName("John") } returns "Firstname: John"
            every { lastName("Doe") } returns "Lastname: Doe"
            every { position("F") } returns "Position: F"
            every { height(5, 11) } returns "Height: 5 feet 11 inches"
            every { weight(42) } returns "Weight: 42"
            every { teamName("Lakers") } returns "Team: Lakers"
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
            strings = strings,
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
    fun `should show position when it is not missing`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(position = "F")
        }
        val viewModel = viewModel(loadPlayer = loadPlayer)

        viewModel.state.value.isPositionVisible shouldBe true
    }

    @Test
    fun `should hide position when it is missing`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(position = "")
        }
        val viewModel = viewModel(loadPlayer = loadPlayer)

        viewModel.state.value.isPositionVisible shouldBe false
    }

    @Test
    fun `should show height when it is not missing`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(heightFeet = 1, heightInches = 2)
        }
        val viewModel = viewModel(loadPlayer = loadPlayer)

        viewModel.state.value.isHeightVisible shouldBe true
    }

    @Test
    fun `should hide height when it is missing`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(heightFeet = null, heightInches = null)
        }
        val viewModel = viewModel(loadPlayer = loadPlayer)

        viewModel.state.value.isHeightVisible shouldBe false
    }

    @Test
    fun `should show weight when it is not missing`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(weightPounds = 42)
        }
        val viewModel = viewModel(loadPlayer = loadPlayer)

        viewModel.state.value.isWeightVisible shouldBe true
    }

    @Test
    fun `should hide weight when it is missing`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(weightPounds = null)
        }
        val viewModel = viewModel(loadPlayer = loadPlayer)

        viewModel.state.value.isWeightVisible shouldBe false
    }

    @Test
    fun `should show team when it is not missing`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(team = team())
        }
        val viewModel = viewModel(loadPlayer = loadPlayer)

        viewModel.state.value.isTeamVisible shouldBe true
    }

    @Test
    fun `should hide team when it is missing`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(team = null)
        }
        val viewModel = viewModel(loadPlayer = loadPlayer)

        viewModel.state.value.isTeamVisible shouldBe false
    }

    @Test
    fun `should navigate to team detail`() {
        val navigation: PlayerDetailNavigationController = mockk(relaxUnitFun = true)
        val viewModel = viewModel(navigation = navigation)

        viewModel.onTeam()

        verify { navigation.goToTeamDetail() }
    }

    private fun viewModel(
        strings: PlayerDetailStrings = mockk(relaxed = true),
        loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player()
        },
        navigation: PlayerDetailNavigationController = mockk(relaxUnitFun = true),
    ) = PlayerDetailViewModel(
        strings = strings,
        loadPlayer = loadPlayer,
        navigation = navigation,
    )
}
