package com.amarnehsoft.holyquran.network.tafseer;

import com.amarnehsoft.holyquran.model.TafseerAyah;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TafseerApi {

    @GET("quran")
    Single<List<GetSurahsNamesResponse>> getSurahsNames();

    @GET("tafseer/{tafseer_id}/{sura_number}/{ayah_number}")
    Single<TafseerAyah> tafseerAyah(@Path("tafseer_id") int tafseer_id,
                                    @Path("sura_number") int sura_number,
                                    @Path("ayah_number") int ayah_number);

}
