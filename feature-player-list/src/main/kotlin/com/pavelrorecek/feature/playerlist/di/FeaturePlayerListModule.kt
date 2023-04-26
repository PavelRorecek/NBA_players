package com.pavelrorecek.feature.playerlist.di

import com.pavelrorecek.feature.playerlist.data.PlayerListRepositoryImpl
import com.pavelrorecek.feature.playerlist.domain.ObservePlayerListUseCase
import com.pavelrorecek.feature.playerlist.domain.PlayerListRepository
import com.pavelrorecek.feature.playerlist.domain.RequestFirstPagePlayerListUseCase
import com.pavelrorecek.feature.playerlist.domain.RequestNextPagePlayerListUseCase
import com.pavelrorecek.feature.playerlist.platform.PlayerListStringsImpl
import com.pavelrorecek.feature.playerlist.presentation.PlayerListStrings
import com.pavelrorecek.feature.playerlist.presentation.PlayerListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

public val featurePlayerListModule: Module = module {
    singleOf(::PlayerListStringsImpl) bind PlayerListStrings::class
    singleOf(::PlayerListRepositoryImpl) bind PlayerListRepository::class
    factoryOf(::RequestFirstPagePlayerListUseCase)
    factoryOf(::RequestNextPagePlayerListUseCase)
    factoryOf(::ObservePlayerListUseCase)
    viewModelOf(::PlayerListViewModel)
}
