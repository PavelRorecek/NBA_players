package com.pavelrorecek.feature.playerdetail.platform

import android.content.Context
import com.pavelrorecek.feature.playerdetail.R
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

internal class PlayerDetailStringsImplTest {

    @Test
    fun `should return title string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.player_detail_fullname, "John", "Doe")
            } returns "Title"
        }
        val strings = strings(context)

        strings.title("John", "Doe") shouldBe "Title"
    }

    @Test
    fun `should return first name string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.player_detail_firstname, "John")
            } returns "First name"
        }
        val strings = strings(context)

        strings.firstName("John") shouldBe "First name"
    }

    @Test
    fun `should return last name string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.player_detail_lastname, "Doe")
            } returns "Last name"
        }
        val strings = strings(context)

        strings.lastName("Doe") shouldBe "Last name"
    }

    @Test
    fun `should return position string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.player_detail_position, "A")
            } returns "Position: A"
        }
        val strings = strings(context)

        strings.position("A") shouldBe "Position: A"
    }

    @Test
    fun `should return height string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.player_detail_height, 5, 11)
            } returns "Height: 5 feet 11 inches"
        }
        val strings = strings(context)

        strings.height(5, 11) shouldBe "Height: 5 feet 11 inches"
    }

    @Test
    fun `should return weight string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.player_detail_weight, 12)
            } returns "Weight: 12"
        }
        val strings = strings(context)

        strings.weight(12) shouldBe "Weight: 12"
    }

    @Test
    fun `should return team name string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.player_detail_team, "Lakers")
            } returns "Team: Lakers"
        }
        val strings = strings(context)

        strings.teamName("Lakers") shouldBe "Team: Lakers"
    }

    private fun strings(
        context: Context = mockk(relaxed = true),
    ) = PlayerDetailStringsImpl(context)
}
