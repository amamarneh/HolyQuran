package com.amarnehsoft.holyquran.db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;
import android.support.annotation.NonNull;

import com.amarnehsoft.holyquran.model.Aya;
import com.amarnehsoft.holyquran.model.Surah;

@Database(entities = {Surah.class, Aya.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    abstract SuraDao suraDao();
    abstract AyaDao ayaDao();
}
