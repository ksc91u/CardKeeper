package com.awscherb.cardkeeper.ui.base

import android.app.Application
import androidx.multidex.MultiDexApplication

import com.awscherb.cardkeeper.di.component.DaggerViewComponent
import com.awscherb.cardkeeper.di.component.ViewComponent
import com.awscherb.cardkeeper.di.module.AppModule
import com.awscherb.cardkeeper.di.module.DaoModule
import com.awscherb.cardkeeper.di.module.SchedulerModule


class CardKeeperApplication : MultiDexApplication() {

    lateinit var viewComponent: ViewComponent
        private set

    override fun onCreate() {
        super.onCreate()


        viewComponent = DaggerViewComponent.builder()
            .appModule(AppModule(this))
            .daoModule(DaoModule())
            .schedulerModule(SchedulerModule())
            .build()

    }
}
