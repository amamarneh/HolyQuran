package com.amarnehsoft.holyquran.fragments.verses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class VersesFragmentViewModel @Inject
constructor() : ViewModel() {

    val resultLive = MutableLiveData<List<Int>>()
    val error = MutableLiveData<String>()
    var showProgress = MutableLiveData<Boolean>()

    var numberOfVerses = 0
    var numberOfStartAya: Int = 0

    fun init() {
        showProgress.postValue(true)
        this.numberOfVerses = numberOfVerses
        Single.just(numberOfVerses)
            .map<List<Int>> { integer ->
                val list = ArrayList<Int>()
                for (i in 1..integer) {
                    list.add(i)
                }
                list
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Int>>() {
                override fun onSuccess(integers: List<Int>) {
                    resultLive.postValue(integers)
                    showProgress.postValue(false)
                }

                override fun onError(e: Throwable) {
                    showProgress.postValue(false)
                    error.postValue(e.message)
                }
            })
    }
}
