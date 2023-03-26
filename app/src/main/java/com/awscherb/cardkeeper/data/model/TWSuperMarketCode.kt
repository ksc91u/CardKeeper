package com.awscherb.cardkeeper.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import mlkit.BarCode
import mlkit.BarcodeFormat

fun sumOfIndex(displayValue: String, mod: Int, ignore: List<Int>): Int {
    return displayValue.asSequence().mapIndexed { index, c ->
        if (ignore.contains(index)) return@mapIndexed 0
        if (index % 2 == mod) {
            return@mapIndexed getCodeOf(c)
        } else
            return@mapIndexed 0
    }.sum()
}

fun checksum(code: TWSuperMarketCode): Boolean {
    if (code.code9 == null || code.code16 == null) return false
    val singleSum9 = sumOfIndex(code.code9!!.displayValue, 0, emptyList())
    val singleSum16 = sumOfIndex(code.code16!!.displayValue, 0, emptyList())
    val singleSum15a = sumOfIndex(code.code15.first().displayValue, 0, listOf(4, 5))
    val singleSum15b = sumOfIndex(code.code15.last().displayValue, 0, listOf(4, 5))

    val doubleSum9 = sumOfIndex(code.code9!!.displayValue, 1, emptyList())
    val doubleSum16 = sumOfIndex(code.code16!!.displayValue, 1, emptyList())
    val doubleSum15a = sumOfIndex(code.code15.first().displayValue, 1, listOf(4, 5))
    val doubleSum15b = sumOfIndex(code.code15.last().displayValue, 1, listOf(4, 5))

    val remainA1 = ((singleSum9 + singleSum16 + singleSum15a) % 11).let {
        return@let if (it == 0) "A" else if (it == 10) "B" else "$it"
    }
    val remainA2 = ((doubleSum9 + doubleSum16 + doubleSum15a) % 11).let {
        return@let if (it == 0) "X" else if (it == 10) "Y" else "$it"
    }

    println(">>> Checksum $remainA1$remainA2 ")
    return (code.code15.first().displayValue.substring(4, 6) == "$remainA1$remainA2")
}

private fun getCodeOf(char: Char): Int {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
    val code = "123456789123456789234567891234567890"

    return Integer.parseInt(code[chars.indexOf(char)].toString())
}

@Parcelize
data class TWSuperMarketCode(
    var code15: HashSet<BarCode> = HashSet(),
    var code9: BarCode? = null,
    var code16: BarCode? = null
): Parcelable {

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
            if (it.rawValue.length == 15) {
                code15.add(it)
            }
        }
    }

    fun isComplete(requireCheckSum: Boolean): Boolean {
        if (code9 == null || code16 == null || code15.size == 0) return false
        if (!requireCheckSum) return true
        if (!checksum(this)) {
            code9 = null
            code16 = null
            code15.clear()
            return false
        }
        return checksum(this)
    }
}