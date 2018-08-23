package com.amarnehsoft.holyquran.db;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {
    @Provides
    @Singleton
    public AppDatabase provideDB(Context context){
        return Room.databaseBuilder(context,
                AppDatabase.class, "holy-quran").allowMainThreadQueries().build();
    }

    @Provides
    @Singleton
    public SuraDao provideSurahDao(AppDatabase database){
        return database.suraDao();
    }

    @Provides
    @Singleton
    public AyaDao provideAyaDoa(AppDatabase database){
        return database.ayaDao();
    }
}
