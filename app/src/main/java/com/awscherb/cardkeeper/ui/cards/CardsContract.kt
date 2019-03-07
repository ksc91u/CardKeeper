package com.awscherb.cardkeeper.ui.cards

import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.model.TwCode
import com.awscherb.cardkeeper.ui.base.BasePresenter
import com.awscherb.cardkeeper.ui.base.BaseView

interface CardsContract {

    interface View : BaseView {

        fun showCards(codes: List<TwCode>)

        fun onCardAdded(code: TwCode)

        fun onCardDeleted()

    }

    interface Presenter : BasePresenter<CardsContract.View> {

        fun loadCards()

        fun addNewCard(code: TwCode)

        fun deleteCard(code: TwCode)

        fun swapCard(code0: TwCode, code1: TwCode)

    }


}
