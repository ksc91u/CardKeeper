package com.awscherb.cardkeeper.ui.cards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.data.model.TwCode
import com.awscherb.cardkeeper.databinding.AdapterCodeBinding
import com.awscherb.cardkeeper.ui.base.BaseAdapter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import github.nisrulz.recyclerviewhelper.RVHAdapter
import mlkit.BarcodeFormat

class CardViewHolder(
    val binding: AdapterCodeBinding
): RecyclerView.ViewHolder(binding.root)

class CardsAdapter constructor(
    private val context: FragmentActivity,
    presenter: CardsContract.Presenter,
    private val deleteListener: (TwCode) -> Unit
): BaseAdapter<TwCode, CardViewHolder>(presenter), RVHAdapter {
    override fun onItemDismiss(position: Int, direction: Int) {
        deleteListener(objects[position])
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        swap(fromPosition, toPosition)
        presenter.swapCard(objects[fromPosition], objects[toPosition])
        return true
    }

    private val encoder: BarcodeEncoder = BarcodeEncoder()
    private var payAll: Boolean = false

    //================================================================================
    // Adapter methods
    //================================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(
            AdapterCodeBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CardViewHolder, item: TwCode) {
//        val display = context.windowManager.defaultDisplay
//        val metrics = DisplayMetrics()
//        display.getMetrics(metrics)
//        val widthPixel = metrics.widthPixels

        with(holder.binding) {

            codeData15a.visibility = View.GONE
            codeImage15a.visibility = View.GONE
            // Set title
            codeData9.text = item.code9
            codeData16.text = item.code16
            codeData15.text = if (payAll) (item.sortedCode()
                .first() + "/" + item.payment().first) + " 最低"
            else (item.sortedCode().last() + "/" + item.payment().second) + " 拳腳"

            // Set image scaleType according to barcode type
            when (item.format) {
                BarcodeFormat.QR_CODE, BarcodeFormat.AZTEC, BarcodeFormat.DATA_MATRIX -> {
                    codeImage9.scaleType = ImageView.ScaleType.FIT_CENTER
                    codeImage15.scaleType = ImageView.ScaleType.FIT_CENTER
                    codeImage15a.scaleType = ImageView.ScaleType.FIT_CENTER
                    codeImage16.scaleType = ImageView.ScaleType.FIT_CENTER
                }

                else -> {
                    codeImage9.scaleType = ImageView.ScaleType.FIT_XY
                    codeImage16.scaleType = ImageView.ScaleType.FIT_XY
                    codeImage15.scaleType = ImageView.ScaleType.FIT_XY
                    codeImage15a.scaleType = ImageView.ScaleType.FIT_XY
                }
            }

            // Load image
            try {
                codeImage9.setImageBitmap(
                    encoder.encodeBitmap(item.code9, item.format.toZxing(), 200, 200)
                )
                codeImage16.setImageBitmap(
                    encoder.encodeBitmap(item.code16, item.format.toZxing(), 200, 200)
                )
                codeImage15.setImageBitmap(
                    encoder.encodeBitmap(
                        if (payAll) item.sortedCode().first() else item.sortedCode().last(),
                        item.format.toZxing(),
                        200,
                        200
                    )
                )
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }
    }

    fun switch() {
        payAll = !payAll
        notifyDataSetChanged()
    }
}
