package com.pavelrorecek.app

import android.app.Application
import com.pavelrorecek.core.network.di.coreNetworkModule
import com.pavelrorecek.core.player.di.corePlayerModule
import com.pavelrorecek.feature.playerlist.di.featurePlayerListModule
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
                coreNetworkModule,
                corePlayerModule,
                featurePlayerListModule
            )
        }
    }
}
