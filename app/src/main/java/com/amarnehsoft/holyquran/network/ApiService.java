package com.amarnehsoft.holyquran.network;

import com.amarnehsoft.holyquran.network.response.GetAyaResponse;
import com.amarnehsoft.holyquran.network.response.GetSurahsListResponse;

import javax.inject.Singleton;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("ayah/{ayahNumber}/ar.alafasy")
    Single<GetAyaResponse> getAyah(@Path("ayahNumber") int ayahNumber);

    @GET("surah")
    Single<GetSurahsListResponse> getSurahsList();

    @GET("ayah/{ayaNumber}/en.asad")
    Single<GetAyaResponse> translateAya(@Path("ayaNumber") int ayaNumber);
}
