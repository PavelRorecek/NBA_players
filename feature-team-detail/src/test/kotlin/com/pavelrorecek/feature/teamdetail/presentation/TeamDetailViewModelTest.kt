package com.pavelrorecek.feature.teamdetail.presentation

import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import com.pavelrorecek.core.player.model.player
import com.pavelrorecek.core.player.model.team
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

internal class TeamDetailViewModelTest {

    @Test
    fun `should throw when team is not stored`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(team = null)
        }

        shouldThrow<IllegalStateException> { viewModel(loadPlayer = loadPlayer) }
    }

    @Test
    fun `should map team name to title`() {
        val loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player(team = team(name = "Lakers"))
        }
        val viewModel = viewModel(
            strings = mockk(relaxed = true) {
                every { title("Lakers") } returns "Title"
            },
            loadPlayer = loadPlayer,
        )

        viewModel.state.value.title shouldBe "Title"
    }

    @Test
    fun `should map team to state`() {
        val strings: TeamDetailStrings = mockk(relaxed = true) {
            every { city("Los Angeles") } returns "City: Los Angeles"
            every { conference("West") } returns "Conference: West"
            every { abbreviation("LAL") } returns "Abbreviation: LAL"
            every { division("Pacific") } returns "Division: Pacific"
            every { fullName("Los Angeles Lakers") } returns "Full name: Los Angeles Lakers"
            every { name("Lakers") } returns "Name: Lakers"
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
        val viewModel = viewModel(
            strings = strings,
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

    private fun viewModel(
        strings: TeamDetailStrings = mockk(relaxed = true),
        loadPlayer: LoadCurrentPlayerUseCase = mockk {
            every { this@mockk.invoke() } returns player()
        },
    ) = TeamDetailViewModel(
        strings = strings,
        loadPlayer = loadPlayer,
    )
}
