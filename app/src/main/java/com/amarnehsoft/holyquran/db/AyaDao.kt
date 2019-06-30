package com.amarnehsoft.holyquran.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AyaDao {

    @Query("select * from ayahentity")
    suspend fun getAll(): List<AyahEntity>

    @Query("select * from ayahentity where number =:number")
    suspend fun getByNumber(number: Int): AyahEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(aya: AyahEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<AyahEntity>)
}
