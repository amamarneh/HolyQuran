package com.amarnehsoft.holyquran.db

import androidx.room.Room
import android.content.Context
import androidx.room.RoomDatabase

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class DBModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(appDatabase: AppDatabase): RoomDatabase {
        return appDatabase
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "holy-quran"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSurahDao(database: AppDatabase): SuraDao {
        return database.suraDao()
    }

    @Provides
    @Singleton
    fun provideAyaDoa(database: AppDatabase): AyaDao {
        return database.ayaDao()
    }
}
