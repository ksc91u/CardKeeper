package com.awscherb.cardkeeper.ui.cards

import android.content.Intent
import android.os.Bundle
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.dao.TwCodeDao
import com.awscherb.cardkeeper.data.model.TwCode
import com.awscherb.cardkeeper.ui.base.BaseActivity
import com.awscherb.cardkeeper.ui.scan.ScanActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.activity_cards.*
import mlkit.BarcodeFormat
import mlkit.LivePreviewActivity
import javax.inject.Inject

class CardsActivity(): BaseActivity() {

    //================================================================================
    // Lifecycle methods
    //================================================================================
    @Inject
    lateinit var twCodeDao: TwCodeDao

    private lateinit var fragment: CardsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewComponent().inject(this)
        setContentView(R.layout.activity_cards)

        setUpToolbar()
        setTitle("CardKeeper")

        cardsFab.setOnClickListener {
            supportFragmentManager.findFragmentById(R.id.container)?.startActivityForResult(
                Intent(this, LivePreviewActivity::class.java), REQUEST_GET_CODE
            )
        }

        switchFab.setOnClickListener {
            fragment.switch()
        }

        fragment = CardsFragment.newInstance()
        insertFragment(fragment)

    }

    companion object {
        const val REQUEST_GET_CODE = 3

    }


}
