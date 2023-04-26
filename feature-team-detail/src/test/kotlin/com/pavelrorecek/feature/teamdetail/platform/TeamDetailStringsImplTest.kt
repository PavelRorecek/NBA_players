package com.pavelrorecek.feature.teamdetail.platform

import android.content.Context
import com.pavelrorecek.feature.teamdetail.R
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

internal class TeamDetailStringsImplTest {

    @Test
    fun `should return title string`() {
        val strings = strings()

        strings.title("Title") shouldBe "Title"
    }

    @Test
    fun `should return city string`() {
        val context: Context = mockk(relaxed = true) {
            every { getString(R.string.team_detail_city, "New York") } returns "City: New York"
        }
        val strings = strings(context)

        strings.city("New York") shouldBe "City: New York"
    }

    @Test
    fun `should return conference string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.team_detail_conference, "New York")
            } returns "Conference: New York"
        }
        val strings = strings(context)

        strings.conference("New York") shouldBe "Conference: New York"
    }

    @Test
    fun `should return abbreviation string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.team_detail_abbreviation, "ABB")
            } returns "Abbreviation: ABB"
        }
        val strings = strings(context)

        strings.abbreviation("ABB") shouldBe "Abbreviation: ABB"
    }

    @Test
    fun `should return division string`() {
        val context: Context = mockk(relaxed = true) {
            every { getString(R.string.team_detail_division, "A") } returns "Division: A"
        }
        val strings = strings(context)

        strings.division("A") shouldBe "Division: A"
    }

    @Test
    fun `should return full name string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.team_detail_full_name, "John Doe")
            } returns "Fullname: John Doe"
        }
        val strings = strings(context)

        strings.fullName("John Doe") shouldBe "Fullname: John Doe"
    }

    @Test
    fun `should return name string`() {
        val context: Context = mockk(relaxed = true) {
            every {
                getString(R.string.team_detail_name, "John Doe")
            } returns "Name: John Doe"
        }
        val strings = strings(context)

        strings.name("John Doe") shouldBe "Name: John Doe"
    }

    private fun strings(
        context: Context = mockk(relaxed = true),
    ) = TeamDetailStringsImpl(context)
}
