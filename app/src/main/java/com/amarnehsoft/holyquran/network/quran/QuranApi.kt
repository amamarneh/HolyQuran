package com.amarnehsoft.holyquran.network.quran

import com.amarnehsoft.holyquran.network.response.ApiResponse

import kotlinx.coroutines.Deferred
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
    fun surahsList(): Deferred<Response<ApiResponse<List<Quran.Surah>>>>

    @GET("ayah/{ayahNumber}/ar.Alafasy")
    fun getAyah(@Path("ayahNumber") ayahNumber: Int): Deferred<Response<ApiResponse<Quran.Ayah>>>

    @GET("ayah/{ayaNumber}/en.asad")
    fun translateAya(@Path("ayaNumber") ayaNumber: Int): Deferred<Response<ApiResponse<Quran.Ayah>>>

    @GET("v1/quran/quran-uthmani")
    fun getQuran(): Deferred<Response<ApiResponse<Quran>>>
}