package com.amarnehsoft.holyquran.network.tafseer

import com.amarnehsoft.holyquran.model.TafseerAyah

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TafseerApi {

    @get:GET("quran")
    val surahsNames: Deferred<Response<List<GetSurahsNamesResponse>>>

    @GET("tafseer/{tafseer_id}/{sura_number}/{ayah_number}")
    fun tafseerAyah(
        @Path("tafseer_id") tafseer_id: Int,
        @Path("sura_number") sura_number: Int,
        @Path("ayah_number") ayah_number: Int
    ): Deferred<Response<TafseerAyah>>

    @GET("tafseer")
    fun getTafseerList(): Deferred<Response<List<Tafseer>>>
}

data class Tafseer(
    val id: Long,
    val name: String,
    val language: String,
    val author: String,
    val book_name: String
)