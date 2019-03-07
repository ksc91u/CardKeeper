package com.awscherb.cardkeeper.di.module

import com.awscherb.cardkeeper.data.handler.ScannedCodeHandler
import com.awscherb.cardkeeper.data.handler.TwCodeHandler
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.data.service.TwCodeService
import dagger.Binds
import dagger.Module

@Module
abstract class ServiceModule {
    @Binds
    abstract fun bindScannedCodeService(handler: ScannedCodeHandler): ScannedCodeService

    @Binds
    abstract fun bindTwCodeService(handler: TwCodeHandler): TwCodeService
}