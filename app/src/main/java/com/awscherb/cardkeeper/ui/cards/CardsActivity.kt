package com.awscherb.cardkeeper.ui.cards

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.dao.TwCodeDao
import com.awscherb.cardkeeper.databinding.ActivityCardsBinding
import com.awscherb.cardkeeper.ui.base.BaseActivity
import mlkit.LivePreviewActivity
import javax.inject.Inject

class CardsActivity(): BaseActivity() {

    //================================================================================
    // Lifecycle methods
    //================================================================================
    @Inject
    lateinit var twCodeDao: TwCodeDao

    private lateinit var fragment: CardsFragment

    private lateinit var binding: ActivityCardsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewComponent().inject(this)

        binding = ActivityCardsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setUpToolbar()
        setTitle("CardKeeper")

        binding.cardsFab.setOnClickListener {
            supportFragmentManager.findFragmentById(R.id.container)?.startActivityForResult(
                Intent(this, LivePreviewActivity::class.java), REQUEST_GET_CODE
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
    }
}
