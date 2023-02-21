package com.pavelrorecek.app

import android.app.Application
import com.pavelrorecek.core.network.di.coreNetworkModule
import com.pavelrorecek.core.player.di.corePlayerModule
import com.pavelrorecek.feature.playerdetail.di.featurePlayerDetailModule
import com.pavelrorecek.feature.playerdetail.domain.PlayerDetailNavigationController
import com.pavelrorecek.feature.playerlist.di.featurePlayerListModule
import com.pavelrorecek.feature.playerlist.domain.PlayerListNavigationController
import com.pavelrorecek.feature.teamdetail.di.featureTeamDetailModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.binds
import org.koin.dsl.module

public class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            modules(
                coreNavigationModule,
                coreNetworkModule,
                corePlayerModule,
                featurePlayerDetailModule,
                featurePlayerListModule,
                featureTeamDetailModule,
            )
        }
    }
}

private val coreNavigationModule = module {
    singleOf(::NavigationController) binds arrayOf(
        PlayerListNavigationController::class,
        PlayerDetailNavigationController::class,
    )
}
