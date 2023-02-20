package com.pavelrorecek.core.player.di

import com.pavelrorecek.core.player.data.CurrentPlayerRepositoryImpl
import com.pavelrorecek.core.player.data.PlayerApi
import com.pavelrorecek.core.player.domain.CurrentPlayerRepository
import com.pavelrorecek.core.player.domain.LoadCurrentPlayerUseCase
import com.pavelrorecek.core.player.domain.StoreCurrentPlayerUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

public val corePlayerModule: Module = module {
    factory { get<Retrofit>().create(PlayerApi::class.java) }
    singleOf(::CurrentPlayerRepositoryImpl) bind CurrentPlayerRepository::class
    factoryOf(::StoreCurrentPlayerUseCase)
    factoryOf(::LoadCurrentPlayerUseCase)
}
