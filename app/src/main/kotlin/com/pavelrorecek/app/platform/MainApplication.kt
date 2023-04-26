package com.pavelrorecek.app.platform

import android.app.Application
import com.pavelrorecek.app.di.coreNavigationModule
import com.pavelrorecek.core.network.di.coreNetworkModule
import com.pavelrorecek.core.player.di.corePlayerModule
import com.pavelrorecek.feature.playerdetail.di.featurePlayerDetailModule
import com.pavelrorecek.feature.playerlist.di.featurePlayerListModule
import com.pavelrorecek.feature.teamdetail.di.featureTeamDetailModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

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
