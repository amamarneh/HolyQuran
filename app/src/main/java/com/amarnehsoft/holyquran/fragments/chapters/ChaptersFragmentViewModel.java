package com.amarnehsoft.holyquran.fragments.chapters;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.amarnehsoft.holyquran.Repo;
import com.amarnehsoft.holyquran.model.Surah;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ChaptersFragmentViewModel extends ViewModel{

    public MutableLiveData<Boolean> showProgress = new MutableLiveData<>();

    private MutableLiveData<List<Surah>> resultLive = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    @Inject
    public ChaptersFragmentViewModel(Repo repo){
        showProgress.postValue(true);
        repo.getChaptersList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Surah>>() {
                    @Override
                    public void onSuccess(List<Surah> list) {
                        showProgress.postValue(false);
                        resultLive.postValue(list);
                    }

                    @Override
                    public void onError(Throwable e) {
                        showProgress.postValue(false);
                        error.postValue(e.getMessage());
                    }
                });
    }

    public MutableLiveData<List<Surah>> getResultLive() {
        return resultLive;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
