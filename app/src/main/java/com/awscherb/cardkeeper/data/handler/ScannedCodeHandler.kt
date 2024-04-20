package com.awscherb.cardkeeper.data.handler

import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ScannedCodeHandler constructor(
    var scannedCodeDao: ScannedCodeDao
) : BaseHandler(), ScannedCodeService {

    override fun getScannedCode(codeId: Int): Single<ScannedCode> =
        scannedCodeDao.getScannedCode(codeId).compose(scheduleSingle())

    override fun listAllScannedCodes(): Flowable<List<ScannedCode>> =
        scannedCodeDao.listScannedCodes().compose(scheduleFlowable())

    override fun addScannedCode(scannedCode: ScannedCode): Single<ScannedCode> {
        return Single.fromCallable {
            scannedCodeDao.insertCode(scannedCode)
            scannedCode
        }.compose(scheduleSingle())
    }

    override fun updateScannedCode(scannedCode: ScannedCode): Single<ScannedCode> {
        return Single.fromCallable {
            scannedCodeDao.updateCode(scannedCode)
            scannedCode
        }.compose(scheduleSingle())
    }

    override fun deleteScannedCode(scannedCode: ScannedCode): Completable {
        return Completable.fromCallable {
            scannedCodeDao.deleteCode(scannedCode)
        }.compose(scheduleCompletable())
    }

    override fun swapScannedCode(code0: ScannedCode, code1: ScannedCode): Completable {
        return Completable.fromCallable {
            val index = code1.id
            code1.id = code0.id
            code0.id = index
            scannedCodeDao.updateCode(code0)
            scannedCodeDao.updateCode(code1)
        }.compose(scheduleCompletable())
    }
}