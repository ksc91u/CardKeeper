package com.awscherb.cardkeeper.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import mlkit.BarcodeFormat

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
    lateinit var code15: String
    var created: Long = 0
}