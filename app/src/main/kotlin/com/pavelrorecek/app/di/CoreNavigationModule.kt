package com.pavelrorecek.app.di

import com.pavelrorecek.app.ui.NavigationController
import com.pavelrorecek.feature.playerdetail.domain.PlayerDetailNavigationController
import com.pavelrorecek.feature.playerlist.domain.PlayerListNavigationController
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.binds
import org.koin.dsl.module

internal val coreNavigationModule = module {
    singleOf(::NavigationController) binds arrayOf(
        PlayerListNavigationController::class,
        PlayerDetailNavigationController::class,
    )
}
