package com.awscherb.cardkeeper.di.module.koin

import androidx.room.Room
import com.awscherb.cardkeeper.data.CardKeeperDatabase
import com.awscherb.cardkeeper.data.CardKeeperDatabase_Impl
import com.awscherb.cardkeeper.data.DatabaseMigrations
import com.awscherb.cardkeeper.data.handler.ScannedCodeHandler
import com.awscherb.cardkeeper.data.handler.TwCodeHandler
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.data.service.TwCodeService
import com.awscherb.cardkeeper.ui.cards.CardsPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val appModule = module {
    single<CardKeeperDatabase_Impl> {
        val db = Room.databaseBuilder(
            androidContext(),
            CardKeeperDatabase::class.java, "cardkeeper.db"
        )
            .addMigrations(DatabaseMigrations.MIGRATE_10_TO_11)
            .build()
        return@single db as CardKeeperDatabase_Impl
    }
}

val daoModule = module {
    factory { (db: CardKeeperDatabase_Impl) ->
        db.scannedCodeDao()
    }
    factory { (db: CardKeeperDatabase_Impl) ->
        db.twCodeDao()
    }
    single {
        ScannedCodeHandler(get(parameters = { parametersOf(get<CardKeeperDatabase_Impl>()) })) as ScannedCodeService
    }
    single {
        TwCodeHandler(get(parameters = { parametersOf(get<CardKeeperDatabase_Impl>()) })) as TwCodeService
    }
}

val presenterModule = module {
    factory {
        CardsPresenter(AndroidSchedulers.mainThread(), get(), get())
    }
}