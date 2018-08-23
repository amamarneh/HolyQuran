package com.amarnehsoft.holyquran.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.amarnehsoft.holyquran.model.Aya;
import com.amarnehsoft.holyquran.model.Surah;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface AyaDao {
    @Query("select * from aya where number =:number")
    Aya getByNumber(int number);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Aya aya);
}
