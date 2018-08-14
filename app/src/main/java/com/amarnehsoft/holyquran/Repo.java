package com.amarnehsoft.holyquran;

import com.amarnehsoft.holyquran.model.Aya;
import com.amarnehsoft.holyquran.network.ApiService;
import com.amarnehsoft.holyquran.network.response.GetAyaResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class Repo {

    private ApiService apiService;

    @Inject
    public Repo(ApiService apiService){
        this.apiService = apiService;
    }

    public Single<Aya> getAya(int number){
        return apiService.getAyah(number)
                .map(new Function<GetAyaResponse, Aya>() {
                    @Override
                    public Aya apply(GetAyaResponse getAyaResponse) throws Exception {
                        return getAyaResponse.getData();
                    }
                });
    }
}
