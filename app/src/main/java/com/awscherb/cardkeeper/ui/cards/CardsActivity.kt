package com.awscherb.cardkeeper.ui.cards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.dao.TwCodeDao
import com.awscherb.cardkeeper.ui.base.BaseActivity
import com.awscherb.cardkeeper.databinding.ActivityCardsBinding
import mlkit.barcodescanning.MultipleCodeScanActivity
import org.koin.android.ext.android.inject

class CardsActivity(): BaseActivity() {

    //================================================================================
    // Lifecycle methods
    //================================================================================
    private val twCodeDao: TwCodeDao by inject()

    private lateinit var fragment: CardsFragment

    private lateinit var binding: ActivityCardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCardsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setUpToolbar()
        setTitle("CardKeeper")

        binding.cardsFab.setOnClickListener {
            supportFragmentManager.findFragmentById(R.id.container)?.startActivityForResult(
                Intent(this, MultipleCodeScanActivity::class.java), REQUEST_GET_CODE
            )
        }

        binding.billsFab.setOnClickListener {
            supportFragmentManager.findFragmentById(R.id.container)?.startActivityForResult(
                Intent(this, MultipleCodeScanActivity::class.java)
                    .apply {
                        putExtra("CHECKSUM", false)
                    }, REQUEST_GET_CODE_BILL
            )
        }

        binding.switchFab.setOnClickListener {
            fragment.switch()
        }

        fragment = CardsFragment.newInstance()
        insertFragment(fragment)
    }

    companion object {
        const val REQUEST_GET_CODE = 3
        const val REQUEST_GET_CODE_BILL = 4
    }
}
