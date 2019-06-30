package com.amarnehsoft.holyquran.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SuraDao {
    @Query("select * from suraentity")
    suspend fun all(): List<SuraEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<SuraEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(surah: SuraEntity)

    @Query("select * from suraentity where number =:surahNumber")
    suspend fun getSurah(surahNumber: Int): SuraEntity?
}
