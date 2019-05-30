package com.awscherb.cardkeeper.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.awscherb.cardkeeper.data.dao.ScannedCodeDao
import com.awscherb.cardkeeper.data.dao.TwCodeDao
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.model.TwCode
import com.awscherb.cardkeeper.util.db.BarcodeConverters
import com.awscherb.cardkeeper.util.db.HashSetTypeConverter

@Database(entities = [(ScannedCode::class), (TwCode::class)], version = 11)
@TypeConverters(BarcodeConverters::class, HashSetTypeConverter::class)
abstract class CardKeeperDatabase: RoomDatabase() {
    abstract fun scannedCodeDao(): ScannedCodeDao
    abstract fun twCodeDao(): TwCodeDao
}