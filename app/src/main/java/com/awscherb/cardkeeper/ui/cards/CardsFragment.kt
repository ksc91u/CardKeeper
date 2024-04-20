package com.awscherb.cardkeeper.ui.cards

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.TWSuperMarketCode
import com.awscherb.cardkeeper.data.model.TwCode
import com.awscherb.cardkeeper.databinding.FragmentCardsBinding
import com.awscherb.cardkeeper.ui.base.BaseFragment
import github.nisrulz.recyclerviewhelper.RVHItemTouchHelperCallback
import mlkit.BarcodeFormat
import org.koin.android.ext.android.inject

class CardsFragment: BaseFragment<FragmentCardsBinding>(
    { inflater -> FragmentCardsBinding.inflate(inflater) }
), CardsContract.View {

    private val presenter: CardsPresenter by inject()

    private lateinit var layoutManager: androidx.recyclerview.widget.GridLayoutManager
    private lateinit var scannedCodeAdapter: CardsAdapter

    private var spanCount = 1

    //================================================================================
    // Lifecycle methods
    //================================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            2
        } else {
            1
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
//        setupListeners()

        presenter.attachView(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadCards()
    }

    override fun onPause() {
        presenter.onViewDestroyed()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == CardsActivity.REQUEST_GET_CODE ||
                requestCode == CardsActivity.REQUEST_GET_CODE_BILL)
            && resultCode == RESULT_OK
        ) {

            val code = data!!.getParcelableExtra<TWSuperMarketCode>("TWCODE")
            var twCode = TwCode()
            twCode.code9 = code?.code9?.rawValue ?: ""
            twCode.code15 = code?.code15?.map { it.rawValue }?.toHashSet() ?: hashSetOf<String>()
            twCode.code16 = code?.code16?.rawValue ?: ""
            twCode.format = code?.code9?.barcodeFormat ?: BarcodeFormat.CODE_39
            twCode.isCreditCard = requestCode == CardsActivity.REQUEST_GET_CODE

            presenter.addNewCard(twCode)

//            val scannedCode = ScannedCode()
//            scannedCode.text = data!!.getStringExtra(ScanFragment.EXTRA_BARCODE_TEXT)
//            scannedCode.format =
//                    data.getSerializableExtra(ScanFragment.EXTRA_BARCODE_FORMAT) as BarcodeFormat
//
//            val input = EditText(activity).apply {
//                setHint(R.string.dialog_card_name_hint)
//                inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
//            }
//
//            AlertDialog.Builder(activity!!)
//                .setTitle(R.string.app_name)
//                .setView(input)
//                .setPositiveButton(R.string.action_add) { _, _ ->
//                    scannedCode.title = input.text.toString()
//                    presenter.addNewCard(scannedCode)
//                }
//                .setNegativeButton(R.string.action_cancel) { dialog, _ -> dialog.dismiss() }
//                .show()
        }
    }

    //================================================================================
    // View methods
    //================================================================================

    override fun showCards(codes: List<TwCode>) {
        binding.total.text = codes.sumOf { it.payment().second }.toString()
        scannedCodeAdapter.swapObjects(codes)
    }

    override fun onCardAdded(code: TwCode) {
        showSnackbar(getString(R.string.fragment_cards_added_card, code.code9))
    }

    override fun onCardDeleted() {
        showSnackbar(R.string.fragment_cards_deleted_card)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    //================================================================================
    // Helper methods
    //================================================================================

    private fun setupRecycler() {
        layoutManager = androidx.recyclerview.widget.GridLayoutManager(activity, spanCount)
        scannedCodeAdapter = CardsAdapter(requireActivity(), presenter) { code ->
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.adapter_scanned_code_delete_message)
                .setPositiveButton(R.string.action_delete) { _, _ ->
                    presenter.deleteCard(code)
                }
                .setNegativeButton(R.string.action_cancel) { _, _ ->
                    scannedCodeAdapter.notifyDataSetChanged()
                }
                .show()
        }

        val callback = RVHItemTouchHelperCallback(
            scannedCodeAdapter, false, false,
            true
        )
        val helper = ItemTouchHelper(callback)
        with(binding) {
            helper.attachToRecyclerView(cardsRecycler)

            cardsRecycler.layoutManager = layoutManager
            cardsRecycler.adapter = scannedCodeAdapter
        }
    }

    fun switch() {
        scannedCodeAdapter.switch()
    }

    companion object {
        fun newInstance() = CardsFragment()
    }
}
