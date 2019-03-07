package com.awscherb.cardkeeper.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import mlkit.BarCode
import mlkit.BarcodeFormat

@Parcelize
data class TWSuperMarketCode(var code9: BarCode? = null,
                             var code16: BarCode? = null,
                             var code15: BarCode? = null) : Parcelable {

    fun addCodes(list: List<BarCode>) {
        list.filter {
            it.barcodeFormat == BarcodeFormat.CODE_39
        }.forEach {
            if (it.rawValue.length == 9 && code9 == null) {
                code9 = it
            }
            if (it.rawValue.length == 16 && code16 == null) {
                code16 = it
            }
            if (it.rawValue.length == 15 && code15 == null) {
                code15 = it
            }
        }
    }

    fun isComplete(): Boolean {
        return (code9 != null && code16 != null && code15 != null)
    }
}