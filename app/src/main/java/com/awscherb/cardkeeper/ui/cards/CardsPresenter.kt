package com.awscherb.cardkeeper.ui.cards

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.model.TwCode
import com.awscherb.cardkeeper.data.service.ScannedCodeService
import com.awscherb.cardkeeper.data.service.TwCodeService
import com.awscherb.cardkeeper.ui.base.Presenter
import io.reactivex.Scheduler

import javax.inject.Inject
import javax.inject.Named

class CardsPresenter @Inject constructor(
    uiScheduler: Scheduler,
    private val scannedCodeService: ScannedCodeService,
    private val twCodeService: TwCodeService
) : Presenter<CardsContract.View>(uiScheduler), CardsContract.Presenter {

    override fun loadCards() {
        addDisposable(
            twCodeService.listAllScannedCodes()
                .compose(scheduleFlowable())
                .subscribe({ view?.showCards(it) },
                    { view?.onError(it) })
        )
    }

    override fun addNewCard(code: TwCode) {
        addDisposable(
                twCodeService.addScannedCode(code)
                .compose(scheduleSingle())
                .subscribe({ view?.onCardAdded(it) },
                    { view?.onError(it) })
        )
    }

    override fun deleteCard(code: TwCode) {
        addDisposable(
                twCodeService.deleteScannedCode(code)
                .compose(scheduleCompletable())
                .subscribe({ view?.onCardDeleted() },
                    { view?.onError(it) })
        )
    }

    override fun swapCard(code0: TwCode, code1: TwCode) {
        addDisposable(
                twCodeService.swapScannedCode(code0, code1)
                        .compose(scheduleCompletable())
                        .subscribe({ },
                                { view?.onError(it) })
        )
    }
}
