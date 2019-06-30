package com.amarnehsoft.holyquran.network

import com.amarnehsoft.holyquran.model.Result
import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.model.TafseerAyah
import com.amarnehsoft.holyquran.network.quran.Quran
import com.amarnehsoft.holyquran.network.tafseer.Tafseer

interface RemoteDataSource {
    suspend fun getChaptersList(): Result<List<Surah>>
    suspend fun getAyah(ayahNumber: Int): Result<Quran.Ayah>
    suspend fun translateAya(ayaNumber: Int): Result<Quran.Ayah>
    suspend fun getQuran(): Result<Quran>

    //tafseer api
    suspend fun tafseerAyah(
        tafseer_id: Int,
        sura_number: Int,
        ayah_number: Int
    ): Result<TafseerAyah>

    suspend fun getTafseerList(): Result<List<Tafseer>>
}