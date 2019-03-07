package com.awscherb.cardkeeper.data.service


import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.model.TwCode

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


interface ScannedCodeService {

    fun getScannedCode(codeId: Int): Single<ScannedCode>
    fun listAllScannedCodes(): Flowable<List<ScannedCode>>

    fun addScannedCode(scannedCode: ScannedCode): Single<ScannedCode>

    fun updateScannedCode(scannedCode: ScannedCode): Single<ScannedCode>

    fun deleteScannedCode(scannedCode: ScannedCode): Completable

    fun swapScannedCode(code0: ScannedCode, code1: ScannedCode): Completable

}

interface TwCodeService {

    fun getScannedCode(codeId: Int): Single<TwCode>
    fun listAllScannedCodes(): Flowable<List<TwCode>>

    fun addScannedCode(scannedCode: TwCode): Single<TwCode>

    fun updateScannedCode(scannedCode: TwCode): Single<TwCode>

    fun deleteScannedCode(scannedCode: TwCode): Completable

    fun swapScannedCode(code0: TwCode, code1: TwCode): Completable

}
