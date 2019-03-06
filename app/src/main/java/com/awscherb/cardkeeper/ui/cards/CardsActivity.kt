package com.awscherb.cardkeeper.ui.cards

import android.content.Intent
import android.os.Bundle
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.dao.TwCodeDao
import com.awscherb.cardkeeper.data.model.TwCode
import com.awscherb.cardkeeper.ui.base.BaseActivity
import com.awscherb.cardkeeper.ui.scan.ScanActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewComponent().inject(this)
        setContentView(R.layout.activity_cards)

        setUpToolbar()
        setTitle("CardKeeper")

        object: Thread() {
            override fun run() {
                twCodeDao.insertCode(TwCode().apply {
                    code15="151515"
                    code16 = "161616"
                    code9 = "090909"
                    format = BarcodeFormat.CODE_39
                })

                twCodeDao.listScannedCodes().subscribe{
                    it.forEach {
                        println(">>>>> ${it.code15}, ${it.code16}, ${it.code9}, ${it.format}")
                    }
                }
            }
        }.start()

        cardsFab.setOnClickListener {
            supportFragmentManager.findFragmentById(R.id.container)?.startActivityForResult(
                Intent(this, LivePreviewActivity::class.java), REQUEST_GET_CODE
            )
        }

        insertFragment(CardsFragment.newInstance())

    }

    companion object {
        const val REQUEST_GET_CODE = 3

    }


}
