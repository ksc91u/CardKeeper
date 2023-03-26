package com.awscherb.cardkeeper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import mlkit.BarcodeFormat
import java.util.*
import kotlin.collections.HashSet

@Entity(tableName = "scannedCode")
class ScannedCode: BaseModel() {

    @PrimaryKey(autoGenerate = true) var id: Int = 0
    lateinit var format: BarcodeFormat
    lateinit var text: String
    lateinit var title: String
    var created: Long = 0
}

@Entity(tableName = "twCode")
class TwCode: BaseModel() {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
    lateinit var format: BarcodeFormat
    lateinit var code9: String
    lateinit var code16: String
    lateinit var code15: HashSet<String>
    var isCreditCard: Boolean = true
    var created: Long = 0

    fun payment () :Pair<Int, Int>{
        val sorted = code15.map {
            it.substring(9).toInt()
        }.sorted()
        return Pair(sorted.minOrNull()?:0, sorted.maxOrNull()?:0)
    }

    fun sortedCode() : List<String>{
        return code15.sortedBy {
            it.substring(9).toInt()
        }
    }
}