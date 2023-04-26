package com.pavelrorecek.feature.playerlist.platform

import android.content.Context
import com.pavelrorecek.feature.playerlist.R
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

internal class PlayerListStringsImplTest {

    @Test
    fun `should return title string`() {
        val context: Context = mockk(relaxed = true) {
            every { getString(R.string.player_list_title) } returns "Title"
        }
        val strings = strings(context)

        strings.title() shouldBe "Title"
    }

    @Test
    fun `should return error string`() {
        val context: Context = mockk(relaxed = true) {
            every { getString(R.string.player_list_loading_error) } returns "Error"
        }
        val strings = strings(context)

        strings.errorMessage() shouldBe "Error"
    }

    @Test
    fun `should return name string`() {
        val strings = strings()

        strings.name(firstName = "John", lastName = "Doe") shouldBe "John Doe"
    }

    @Test
    fun `should return team name string`() {
        val context: Context = mockk(relaxed = true) {
            every { getString(R.string.player_list_team, "Lakers") } returns "Team: Lakers"
        }
        val strings = strings(context)

        strings.teamName("Lakers") shouldBe "Team: Lakers"
    }

    @Test
    fun `should return position string`() {
        val context: Context = mockk(relaxed = true) {
            every { getString(R.string.player_list_position, "A") } returns "Position: A"
        }
        val strings = strings(context)

        strings.position("A") shouldBe "Position: A"
    }

    private fun strings(
        context: Context = mockk(relaxed = true),
    ) = PlayerListStringsImpl(context)
}
