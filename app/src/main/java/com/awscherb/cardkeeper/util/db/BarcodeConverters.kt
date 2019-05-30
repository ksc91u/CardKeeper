package com.awscherb.cardkeeper.util.db

import androidx.room.TypeConverter
import mlkit.BarcodeFormat

object BarcodeConverters {

    @[TypeConverter JvmStatic]
    fun fromString(string: String): BarcodeFormat = BarcodeFormat.valueOf(string)

    @[TypeConverter JvmStatic]
    fun toString(format: BarcodeFormat): String = format.toString()

}

object HashSetTypeConverter {
    val SEPARATOR = "㍻"

    @[TypeConverter JvmStatic]
    fun fromString(value: String): HashSet<String> {
        if (!value.contains(SEPARATOR)) {
            return hashSetOf(value)
        }
        return value.split(SEPARATOR).toHashSet()
    }

    @[TypeConverter JvmStatic]
    fun toString(value: HashSet<String>): String {
        return value.joinToString(SEPARATOR)
    }
}