package com.pavelrorecek.feature.teamdetail.di

import com.pavelrorecek.feature.teamdetail.platform.TeamDetailStringsImpl
import com.pavelrorecek.feature.teamdetail.presentation.TeamDetailStrings
import com.pavelrorecek.feature.teamdetail.presentation.TeamDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

public val featureTeamDetailModule: Module = module {
    factoryOf(::TeamDetailStringsImpl) bind TeamDetailStrings::class
    viewModelOf(::TeamDetailViewModel)
}
