package com.awscherb.cardkeeper.data.dao

import androidx.room.*
import com.awscherb.cardkeeper.data.model.ScannedCode
import com.awscherb.cardkeeper.data.model.TwCode
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ScannedCodeDao {

    @Query("SELECT * FROM scannedCode WHERE id = :id LIMIT 1")
    fun getScannedCode(id: Int): Single<ScannedCode>

    @Query("SELECT * FROM scannedCode")
    fun listScannedCodes(): Flowable<List<ScannedCode>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCode(code: ScannedCode)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCode(code: ScannedCode)

    @Delete
    fun deleteCode(code: ScannedCode)

}

@Dao
interface TwCodeDao {

    @Query("SELECT * FROM twCode WHERE id = :id LIMIT 1")
    fun getScannedCode(id: Int): Single<TwCode>

    @Query("SELECT * FROM twCode")
    fun listScannedCodes(): Flowable<List<TwCode>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCode(code: TwCode)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCode(code: TwCode)

    @Delete
    fun deleteCode(code: TwCode)

}