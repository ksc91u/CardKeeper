package mlkit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QRCode(val rawValue: String, val displayValue: String) : Parcelable