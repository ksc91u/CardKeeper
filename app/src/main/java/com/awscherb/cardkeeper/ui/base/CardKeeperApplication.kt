package com.awscherb.cardkeeper.ui.base

import androidx.multidex.MultiDexApplication
import com.awscherb.cardkeeper.di.module.koin.appModule
import com.awscherb.cardkeeper.di.module.koin.daoModule
import com.awscherb.cardkeeper.di.module.koin.presenterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CardKeeperApplication: MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CardKeeperApplication)
            modules(
                appModule + daoModule + presenterModule
            )
        }
    }
}
