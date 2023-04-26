package com.pavelrorecek.core.network.platform

import kotlin.coroutines.CoroutineContext

public data class AppDispatchers(
    public val main: CoroutineContext,
    public val io: CoroutineContext
)
