package com.pavelrorecek.core.player.di

import com.pavelrorecek.core.player.data.PlayerApi
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

public val corePlayerModule: Module = module {
    factory { get<Retrofit>().create(PlayerApi::class.java) }
}
