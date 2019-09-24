package com.amarnehsoft.holyquran.network.quran

import com.amarnehsoft.holyquran.network.response.ApiResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * The Retrofit interface for quran data.
 *
 * @see ServiceConstants.quranBaseUrl
 */
interface QuranApi {

    @GET("surah")
    suspend fun surahsList(): Response<ApiResponse<List<Quran.Surah>>>

    @GET("ayah/{ayahNumber}/ar.Alafasy")
    suspend fun getAyah(@Path("ayahNumber") ayahNumber: Int): Response<ApiResponse<Quran.Ayah>>

    @GET("ayah/{ayaNumber}/en.asad")
    suspend fun translateAya(@Path("ayaNumber") ayaNumber: Int): Response<ApiResponse<Quran.Ayah>>

    @GET("v1/quran/quran-uthmani")
    suspend fun getQuran(): Response<ApiResponse<Quran>>
}