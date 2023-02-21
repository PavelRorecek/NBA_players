package com.pavelrorecek.feature.teamdetail.di

import com.pavelrorecek.feature.teamdetail.presentation.TeamDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

public val featureTeamDetailModule: Module = module {
    viewModelOf(::TeamDetailViewModel)
}
