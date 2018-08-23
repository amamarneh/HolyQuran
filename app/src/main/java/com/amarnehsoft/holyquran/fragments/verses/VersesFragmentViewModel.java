package com.amarnehsoft.holyquran.fragments.verses;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.amarnehsoft.holyquran.Repo;
import com.amarnehsoft.holyquran.model.Surah;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class VersesFragmentViewModel extends ViewModel{

    private MutableLiveData<List<Integer>> resultLive = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgress = new MutableLiveData<>();

    private int numberOfVerses = 0;
    private int numberOfStartAya;

    @Inject
    public VersesFragmentViewModel(){

    }

    public void init(){
        showProgress.postValue(true);
        this.numberOfVerses = numberOfVerses;
        Single.just(numberOfVerses)
                .map(integer -> {
                    List<Integer> list = new ArrayList<>();
                    for (int i=1; i <= integer; i++){
                        list.add(i);
                    }
                    return list;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Integer>>() {
                    @Override
                    public void onSuccess(List<Integer> integers) {
                        resultLive.postValue(integers);
                        showProgress.postValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showProgress.postValue(false);
                        error.postValue(e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<Integer>> getResultLive() {
        return resultLive;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public int getNumberOfVerses() {
        return numberOfVerses;
    }

    public void setNumberOfVerses(int numberOfVerses) {
        this.numberOfVerses = numberOfVerses;
    }

    public void setNumberOfStartAya(int numberOfStartAya) {
        this.numberOfStartAya = numberOfStartAya;
    }

    public int getNumberOfStartAya() {
        return numberOfStartAya;
    }
}
