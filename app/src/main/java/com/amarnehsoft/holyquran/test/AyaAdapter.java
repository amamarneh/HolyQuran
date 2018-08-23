package com.amarnehsoft.holyquran.test;

import android.media.MediaPlayer;
import android.util.Pair;
import android.util.SparseArray;

import com.amarnehsoft.holyquran.Repo;
import com.amarnehsoft.holyquran.model.Aya;
import com.amarnehsoft.holyquran.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

public abstract class AyaAdapter {
    private int maxSize = 5;
    private int startIndex = 2;
    private int initialAyaNumber;
    //private Queue<AyaHolder> queue;
    private SparseArray<AyaHolder> arr;
    private SparseArray<AyaHolder> tempArr;
    private boolean withSound;

    private Repo repo;
    private MediaPlayer.OnCompletionListener onCompletionListener;

    public AyaAdapter(Repo repo, int initialAyaNumber, MediaPlayer.OnCompletionListener onCompletionListener, boolean withSound){
        //queue = new ArrayDeque<>();
        arr = new SparseArray<>();
        //use add and poll
        this.repo = repo;
        this.initialAyaNumber = initialAyaNumber;
        this.onCompletionListener = onCompletionListener;
        this.withSound = withSound;
        Timber.w("Amarneh, withSound="+withSound);

        Flowable<Aya> flowable=null;

        for (int i=1; i < maxSize; i++){
            if (i==1){
                flowable = getAya(initialAyaNumber).concatWith(getAya(initialAyaNumber+i));
            }else {
                flowable = flowable.concatWith(getAya(initialAyaNumber+i));
            }
        }

        assert flowable != null;
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSubscriber<Aya>() {
                    int index=0;
                    @Override
                    public void onNext(Aya aya) {
                        index++;
                        if (index == startIndex){
                            queueIsInitialized(arr.valueAt(0));
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        AyaAdapter.this.onError(t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Single<Aya> getAya(int ayaNumber){
        Timber.w("Amarneh, getAya:%s", ayaNumber);
        if (arr.get(ayaNumber) != null)
            return Single.just(arr.get(ayaNumber)).map(AyaHolder::getAya);
        return repo.getAya(ayaNumber)
                .subscribeOn(Schedulers.io())
                .map(aya -> {
                    //queue.add(new AyaHolder(aya));
                    arr.put(aya.getNumber(), new AyaHolder(aya, onCompletionListener, withSound));
                    Timber.e("Amarneh, aya ready:%s", aya.getNumber());
                    return aya;
                });
    }

    public AyaHolder loadNext(){
        AyaHolder firstAyaHolder = null;
        try {
            firstAyaHolder = arr.valueAt(0);
        }catch (ClassCastException e){
            e.printStackTrace();
            return null;
        }

        firstAyaHolder.release();
        arr.removeAt(arr.indexOfValue(firstAyaHolder));

        int number = firstAyaHolder.getAya().getNumber() + maxSize;

        if (arr.get(number) == null && number <= Constants.LAST_AYA_NUMBER){
            getAya(number)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Aya>() {
                        @Override
                        public void onSuccess(Aya aya) {
                            arr.put(number, new AyaHolder(aya, onCompletionListener, withSound));
                        }

                        @Override
                        public void onError(Throwable e) {
                            AyaAdapter.this.onError(e.getMessage());
                        }
                    });
        }
        try {
            return arr.valueAt(0);
        }catch (ClassCastException e){
            e.printStackTrace();
            return null;
        }
    }

    public abstract void queueIsInitialized(AyaHolder ayaHolder);
    public abstract void onError(String error);

    public boolean pause(){
        try {
            return arr.valueAt(0).pause();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean play(){
        try {
            return arr.valueAt(0).play();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void release(){
        for(int i = 0; i < arr.size(); i++) {
            AyaHolder obj = arr.valueAt(i);
            obj.release();
        }
        arr.clear();
    }

    public AyaHolder getFirst(){
        try {
            return arr.valueAt(0);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
