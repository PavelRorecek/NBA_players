package com.pavelrorecek.feature.playerlist.domain

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class RequestNextPagePlayerListUseCaseTest {

    @Test
    fun `should request next page via repository`() = runTest {
        val repository: PlayerListRepository = mockk(relaxUnitFun = true)
        val request = RequestNextPagePlayerListUseCase(repository = repository)

        request()

        coVerify { repository.requestNextPage() }
    }
}
