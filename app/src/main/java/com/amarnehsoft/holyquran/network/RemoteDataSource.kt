package com.amarnehsoft.holyquran.network

import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.model.TafseerAyah
import com.amarnehsoft.holyquran.network.n.RemoteResponse
import com.amarnehsoft.holyquran.network.quran.Quran
import com.amarnehsoft.holyquran.network.tafseer.Tafseer

interface RemoteDataSource {

    suspend fun getChaptersList(): RemoteResponse<List<Surah>, Exception>
    suspend fun getAyah(ayahNumber: Int): RemoteResponse<Quran.Ayah, Exception>
    suspend fun translateAya(ayaNumber: Int): RemoteResponse<Quran.Ayah, Exception>
    suspend fun getQuran(): RemoteResponse<Quran, Exception>

    //tafseer api
    suspend fun tafseerAyah(
            tafseer_id: Int,
            sura_number: Int,
            ayah_number: Int
    ): RemoteResponse<TafseerAyah, Exception>

    suspend fun getTafseerList(): RemoteResponse<List<Tafseer>, Exception>
}