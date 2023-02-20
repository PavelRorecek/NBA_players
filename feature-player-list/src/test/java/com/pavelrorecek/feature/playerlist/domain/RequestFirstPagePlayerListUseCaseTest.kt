package com.pavelrorecek.feature.playerlist.domain

import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class RequestFirstPagePlayerListUseCaseTest {

    @Test
    fun `should request first page via repository`() = runTest {
        val repository: PlayerListRepository = mockk(relaxUnitFun = true)
        val request = RequestFirstPagePlayerListUseCase(repository = repository)

        request()

        coVerify { repository.requestFirstPage() }
    }
}
