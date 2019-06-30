package com.amarnehsoft.holyquran.fragments.chapters

import androidx.lifecycle.MutableLiveData

import com.amarnehsoft.holyquran.repo.Repo
import com.amarnehsoft.holyquran.model.Result
import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.utils.CoroutinesDispatcherProvider
import com.amarnehsoft.holyquran.viewmodel.BaseViewModel

import javax.inject.Inject

class ChaptersFragmentViewModel @Inject constructor(
    repo: Repo,
    dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel(dispatcherProvider) {
    var showProgress = MutableLiveData<Boolean>()

    val resultLive = MutableLiveData<Result<List<Surah>>>()
    val error = MutableLiveData<String>()

    init {
        background {
            val chapters = repo.chaptersList()
            main { resultLive.value = chapters }
        }
    }

//    override fun showLoading() {
//        showProgress.value = true
//    }
//
//    override fun hideLoading() {
//        showProgress.value = false
//    }
}
