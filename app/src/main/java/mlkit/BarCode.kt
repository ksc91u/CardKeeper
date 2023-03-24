package mlkit

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BarCode(val rawValue: String,
                   val displayValue: String,
                   val barcodeFormat: BarcodeFormat) : Parcelable