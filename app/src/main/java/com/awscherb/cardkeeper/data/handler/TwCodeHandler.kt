package com.awscherb.cardkeeper.data.handler

import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.dao.TwCodeDao
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.model.TwCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.data.service.TwCodeService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class TwCodeHandler @Inject constructor(
    var twCodeDao: TwCodeDao
) : BaseHandler(), TwCodeService {

    override fun getScannedCode(codeId: Int): Single<TwCode> =
            twCodeDao.getScannedCode(codeId).compose(scheduleSingle())

    override fun listAllScannedCodes(): Flowable<List<TwCode>> =
            twCodeDao.listScannedCodes().compose(scheduleFlowable())

    override fun addScannedCode(scannedCode: TwCode): Single<TwCode> {
        return Single.fromCallable {
            twCodeDao.insertCode(scannedCode)
            scannedCode
        }.compose(scheduleSingle())
    }

    override fun updateScannedCode(scannedCode: TwCode): Single<TwCode> {
        return Single.fromCallable {
            twCodeDao.updateCode(scannedCode)
            scannedCode
        }.compose(scheduleSingle())
    }

    override fun deleteScannedCode(scannedCode: TwCode): Completable {
        return Completable.fromCallable {
            twCodeDao.deleteCode(scannedCode)
        }.compose(scheduleCompletable())
    }

    override fun swapScannedCode(code0: TwCode, code1: TwCode): Completable {
        return Completable.fromCallable {
            val index = code1.id
            code1.id = code0.id
            code0.id = index
            twCodeDao.updateCode(code0)
            twCodeDao.updateCode(code1)
        }.compose(scheduleCompletable())
    }
}