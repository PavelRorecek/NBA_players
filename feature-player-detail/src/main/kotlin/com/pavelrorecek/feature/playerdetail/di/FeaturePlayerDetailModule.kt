package com.pavelrorecek.feature.playerdetail.di

import com.pavelrorecek.feature.playerdetail.presentation.PlayerDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

public val featurePlayerDetailModule: Module = module {
    viewModelOf(::PlayerDetailViewModel)
}
