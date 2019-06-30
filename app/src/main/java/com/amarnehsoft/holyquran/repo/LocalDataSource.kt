package com.amarnehsoft.holyquran.repo

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.amarnehsoft.holyquran.db.*
import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.network.quran.Quran
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val ayaDao: AyaDao,
    private val suraDoa: SuraDao,
    private val db: RoomDatabase
) {

    suspend fun getChaptersList(): List<Surah> = suraDoa.all().map {
        Surah(
            it.number,
            it.name,
            it.englishName,
            it.englishNameTranslation,
            0,
            it.revelationType
        )
    }

    suspend fun getAya(ayaNumber: Int): Quran.Ayah = AyahMapper.map(
        ayaDao.getByNumber(ayaNumber)!!
    )

    suspend fun insertChapters(list: List<Surah>) = suraDoa.insert(list.map {
        SuraEntity(
            it.number,
            it.englishName,
            it.englishNameTranslation,
            it.name,
            it.revelationType
        )
    })

    private suspend fun insertVersesList(list: List<AyahEntity>) = ayaDao.insert(list)

    suspend fun insertChaptersAndVerses(chapters: List<Surah>, verses: List<AyahEntity>) {
        db.withTransaction {
            insertChapters(chapters)
            insertVersesList(verses)

        }
    }
}