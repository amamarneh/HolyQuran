package com.amarnehsoft.holyquran.network;

import com.amarnehsoft.holyquran.network.response.GetAyaResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("ayah/{ayahNumber}")
    Single<GetAyaResponse> getAyah(@Path("ayahNumber") int ayahNumber);

}
