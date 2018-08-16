package com.amarnehsoft.holyquran;

import com.amarnehsoft.holyquran.model.Aya;
import com.amarnehsoft.holyquran.network.ApiService;
import com.amarnehsoft.holyquran.network.response.GetAyaResponse;
import com.amarnehsoft.holyquran.network.tafseer.GetSurahsNamesResponse;
import com.amarnehsoft.holyquran.network.tafseer.TafseerApi;
import com.amarnehsoft.holyquran.model.TafseerAyah;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class Repo {

    private ApiService apiService;
    private TafseerApi tafseerApi;

    @Inject
    public Repo(ApiService apiService, TafseerApi tafseerApi){
        this.apiService = apiService;
        this.tafseerApi = tafseerApi;
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

    public Single<List<GetSurahsNamesResponse>> getSurahsNames(){
        return tafseerApi.getSurahsNames();
    }

    public Single<TafseerAyah> tafseerAyah(Aya aya){
        return tafseerApi.tafseerAyah(1, aya.getSurah().getNumber(), aya.getNumberInSurah());
    }
}
