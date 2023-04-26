package com.pavelrorecek.feature.playerdetail.di

import com.pavelrorecek.feature.playerdetail.platform.PlayerDetailStringsImpl
import com.pavelrorecek.feature.playerdetail.presentation.PlayerDetailStrings
import com.pavelrorecek.feature.playerdetail.presentation.PlayerDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

public val featurePlayerDetailModule: Module = module {
    factoryOf(::PlayerDetailStringsImpl) bind PlayerDetailStrings::class
    viewModelOf(::PlayerDetailViewModel)
}
