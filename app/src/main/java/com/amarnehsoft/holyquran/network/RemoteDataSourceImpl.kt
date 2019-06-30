package com.amarnehsoft.holyquran.network

import com.amarnehsoft.holyquran.model.Result
import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.model.TafseerAyah
import com.amarnehsoft.holyquran.network.quran.Quran
import com.amarnehsoft.holyquran.network.quran.QuranApi
import com.amarnehsoft.holyquran.network.tafseer.Tafseer
import com.amarnehsoft.holyquran.network.tafseer.TafseerApi
import com.amarnehsoft.holyquran.utils.safeApiCall
import java.io.IOException
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val quranApi: QuranApi,
    private val tafseerApiService: TafseerApi
) : RemoteDataSource {

    override suspend fun getTafseerList(): Result<List<Tafseer>> {
        return safeApiCall(
            call = { getTafseerListResult() },
            errorMessage = "can't get tafseer list"
        )
    }

    private suspend fun getTafseerListResult(): Result<List<Tafseer>> {
        val response = tafseerApiService.getTafseerList().await()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!)
        } else {
            Result.Error(IOException("can't get quran!!"))
        }
    }

    override suspend fun getQuran(): Result<Quran> {
        return safeApiCall(
            call = { getQuranResult() },
            errorMessage = "can't get quran"
        )
    }

    private suspend fun getQuranResult(): Result<Quran> {
        val response = quranApi.getQuran().await()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data)
        } else {
            Result.Error(IOException("can't get quran!!"))
        }
    }

    override suspend fun getAyah(ayahNumber: Int): Result<Quran.Ayah> {
        return safeApiCall(
            call = { getAyaResult(ayahNumber) },
            errorMessage = "can't get aya from remoteDataSource!"
        )
    }

    override suspend fun translateAya(ayaNumber: Int): Result<Quran.Ayah> {
        return safeApiCall(
            call = { translateAyaResult(ayaNumber) },
            errorMessage = "can't translate aya"
        )
    }

    override suspend fun getChaptersList(): Result<List<Surah>> {
        return safeApiCall(
            call = { getChaptersListResult() },
            errorMessage = "can't get chapters list!!"
        )
    }

    override suspend fun tafseerAyah(
        tafseer_id: Int,
        sura_number: Int,
        ayah_number: Int
    ): Result<TafseerAyah> {
        return safeApiCall(
            call = { tafseerAyaResult(tafseer_id, sura_number, ayah_number) },
            errorMessage = "can't get tafseer aya"
        )
    }

    private suspend fun getAyaResult(ayaNumber: Int): Result<Quran.Ayah> {
        val response = quranApi.getAyah(ayaNumber).await()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data)
        } else {
            Result.Error(IOException("can't get aya, ayaNumber: $ayaNumber"))
        }
    }

    private suspend fun translateAyaResult(ayaNumber: Int): Result<Quran.Ayah> {
        val response = quranApi.translateAya(ayaNumber).await()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data)
        } else {
            Result.Error(IOException("can't translate aya, ayaNumber: $ayaNumber"))
        }
    }

    private suspend fun getChaptersListResult(): Result<List<Surah>> {
        val response = quranApi.surahsList().await()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!.data.map {
                Surah(
                    it.number,
                    it.name,
                    it.englishName,
                    it.englishNameTranslation,
                    it.ayahs.size,
                    it.revelationType
                )
            })
        } else {
            Result.Error(IOException("can't get chapters list"))
        }
    }

    private suspend fun tafseerAyaResult(
        tafseerId: Int,
        surahNumber: Int,
        ayaNumber: Int
    ): Result<TafseerAyah> {
        val response = tafseerApiService.tafseerAyah(tafseerId, surahNumber, ayaNumber).await()
        return if (response.isSuccessful && response.body() != null) {
            Result.Success(response.body()!!)
        } else {
            Result.Error(IOException("can't get tafseer aya, ayaNumber: $ayaNumber"))
        }
    }
}