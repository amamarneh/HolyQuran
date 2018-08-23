package com.amarnehsoft.holyquran;

import android.arch.persistence.room.EmptyResultSetException;

import com.amarnehsoft.holyquran.db.AyaDao;
import com.amarnehsoft.holyquran.db.SuraDao;
import com.amarnehsoft.holyquran.model.Aya;
import com.amarnehsoft.holyquran.model.Surah;
import com.amarnehsoft.holyquran.network.ApiService;
import com.amarnehsoft.holyquran.network.response.GetAyaResponse;
import com.amarnehsoft.holyquran.network.response.GetSurahsListResponse;
import com.amarnehsoft.holyquran.network.tafseer.GetSurahsNamesResponse;
import com.amarnehsoft.holyquran.network.tafseer.TafseerApi;
import com.amarnehsoft.holyquran.model.TafseerAyah;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import timber.log.Timber;

public class Repo {

    private ApiService apiService;
    private TafseerApi tafseerApi;
    private SuraDao suraDao;
    private AyaDao ayaDao;

    @Inject
    public Repo(ApiService apiService, TafseerApi tafseerApi, SuraDao suraDao, AyaDao ayaDao){
        this.apiService = apiService;
        this.tafseerApi = tafseerApi;
        this.suraDao =suraDao;
        this.ayaDao = ayaDao;
    }

    public Single<Aya> getAya(int number){
        Aya ayaFromDB = getAyaFromDB(number);
        if (ayaFromDB != null){
            return Single.just(ayaFromDB);
        }
        Timber.d("Amarneh, aya not exist in db for aya:"+number);
        return getAyaFromApi(number);
    }

    private Single<Aya> getAyaFromApi(int number){
        return apiService.getAyah(number)
                .zipWith(translateAya(number), (getAyaResponse, s) -> {
                    Aya aya = getAyaResponse.getData();
                    aya.setTranslation(s);
                    return aya;
                })
                .flatMap(aya -> tafseerAyah(aya)
                        .map(tafseerAyah -> {
                            aya.setTafseer(tafseerAyah.getText());
                            aya.setSurahNumber(aya.getSurah().getNumber());
                            ayaDao.insert(aya);
                            suraDao.insert(aya.getSurah());
                            return aya;
                        }));
    }

    private Aya getAyaFromDB(int number){
        Timber.d("Amarneh, getAya from db , number="+number);
        Aya aya =  ayaDao.getByNumber(number);
        if (aya != null){
            aya.setSurah(suraDao.getSurah(aya.getSurahNumber()));
        }
        return aya;
    }

    public Single<List<Surah>> getChaptersList(){
        return getChaptersFromDB()
                .flatMap((Function<List<Surah>, SingleSource<List<Surah>>>) list -> {
                    if (list == null || list.size() != 114)
                        return getChaptersFromApi();
                    return Single.just(list);
                });
    }

    private Single<List<Surah>> getChaptersFromApi(){
        return apiService.getSurahsList().map(getSurahsListResponse -> {
            suraDao.insert(getSurahsListResponse.getData());
            return getSurahsListResponse.getData();
        });
    }

    private Single<List<Surah>> getChaptersFromDB(){
        return suraDao.getAll();
    }

    private Single<TafseerAyah> tafseerAyah(Aya aya){
        return tafseerApi.tafseerAyah(1, aya.getSurah().getNumber(), aya.getNumberInSurah());
    }

    private Single<String> translateAya(int ayaNumber){
        return apiService.translateAya(ayaNumber)
                .map(getAyaResponse -> getAyaResponse.getData().getText());
    }
}
