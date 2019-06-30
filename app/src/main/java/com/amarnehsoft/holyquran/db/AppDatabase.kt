package com.amarnehsoft.holyquran.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [SuraEntity::class, AyahEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun suraDao(): SuraDao
    abstract fun ayaDao(): AyaDao
}
