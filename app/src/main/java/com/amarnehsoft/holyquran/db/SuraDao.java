package com.amarnehsoft.holyquran.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.amarnehsoft.holyquran.model.Surah;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface SuraDao {
    @Query("select * from surah")
    Single<List<Surah>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Surah> list);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Surah surah);

    @Query("select * from surah where number =:surahNumber")
    Surah getSurah(int surahNumber);
}
