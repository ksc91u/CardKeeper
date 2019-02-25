package com.awscherb.cardkeeper.ui.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.awscherb.cardkeeper.R
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.ui.base.BaseAdapter
import com.google.zxing.BarcodeFormat
import com.google.zxing.BarcodeFormat.*
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import github.nisrulz.recyclerviewhelper.RVHAdapter
import kotlinx.android.synthetic.main.adapter_code.view.*


class CardsAdapter constructor(
        private val context: FragmentActivity,
        presenter: CardsContract.Presenter,
        private val deleteListener: (ScannedCode) -> Unit
) : BaseAdapter<ScannedCode>(presenter), RVHAdapter {
    override fun onItemDismiss(position: Int, direction: Int) {
        //deleteListener(objects[position])
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        swap(fromPosition, toPosition)
        presenter.swapCard(objects[fromPosition], objects[toPosition])
        return true
    }

    private val encoder: BarcodeEncoder = BarcodeEncoder()
    private var code128: Boolean = false

    //================================================================================
    // Adapter methods
    //================================================================================

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.adapter_code,
                        parent,
                        false
                )
        ) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ScannedCode) {
//        val display = context.windowManager.defaultDisplay
//        val metrics = DisplayMetrics()
//        display.getMetrics(metrics)
//        val widthPixel = metrics.widthPixels

        holder.itemView.apply {

            // Set title
            codeTitle.text = ""
            codeData.text = item.text
            codeFormat.text = if(code128) mlkit.BarcodeFormat.CODE_128.name else item.format.name

            // Set image scaleType according to barcode type
            when (item.format) {
                QR_CODE, AZTEC, DATA_MATRIX -> codeImage.scaleType = ImageView.ScaleType.FIT_CENTER
                else -> codeImage.scaleType = ImageView.ScaleType.FIT_XY
            }

            // Load image
            try {
                if(code128){
                    codeImage.setImageBitmap(
                            encoder.encodeBitmap(item.text, BarcodeFormat.CODE_128, 200, 200)
                    )
                }else{
                    codeImage.setImageBitmap(
                            encoder.encodeBitmap(item.text, item.format.toZxing(), 200, 200)
                    )
                }
            } catch (e: WriterException) {
                e.printStackTrace()
            }

        }


    }

    fun switch() {
        code128 = !code128
        notifyDataSetChanged()
    }


}
