package com.awscherb.cardkeeper.ui.cards

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import io.reactivex.Scheduler

import javax.inject.Inject
import javax.inject.Named

class CardsPresenter @Inject constructor(
    uiScheduler: Scheduler,
    private val scannedCodeService: ScannedCodeService
) : Presenter<CardsContract.View>(uiScheduler), CardsContract.Presenter {

    override fun loadCards() {
        addDisposable(
            scannedCodeService.listAllScannedCodes()
                .compose(scheduleFlowable())
                .subscribe({ view?.showCards(it) },
                    { view?.onError(it) })
        )
    }

    override fun addNewCard(code: ScannedCode) {
        addDisposable(
            scannedCodeService.addScannedCode(code)
                .compose(scheduleSingle())
                .subscribe({ view?.onCardAdded(it) },
                    { view?.onError(it) })
        )
    }

    override fun deleteCard(code: ScannedCode) {
        addDisposable(
            scannedCodeService.deleteScannedCode(code)
                .compose(scheduleCompletable())
                .subscribe({ view?.onCardDeleted() },
                    { view?.onError(it) })
        )
    }

    override fun swapCard(code0: ScannedCode, code1: ScannedCode) {
        addDisposable(
                scannedCodeService.swapScannedCode(code0, code1)
                        .compose(scheduleCompletable())
                        .subscribe({ },
                                { view?.onError(it) })
        )
    }
}
