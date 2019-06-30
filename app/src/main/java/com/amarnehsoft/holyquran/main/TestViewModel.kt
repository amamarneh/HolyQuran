package com.amarnehsoft.holyquran.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amarnehsoft.holyquran.repo.Repo
import com.amarnehsoft.holyquran.SPController
import com.amarnehsoft.holyquran.model.Aya
import com.amarnehsoft.holyquran.model.Result
import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.network.quran.Quran
import com.amarnehsoft.holyquran.utils.CoroutinesDispatcherProvider
import com.amarnehsoft.holyquran.utils.SingleLiveEvent
import com.amarnehsoft.holyquran.viewmodel.BaseViewModel
import java.io.IOException
import javax.inject.Inject

class TestViewModel @Inject constructor(
    private val repo: Repo,
    private val spController: SPController,
    dispatcherProvider: CoroutinesDispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    private var ayaNumber = spController.ayaNumber
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    val ayaLiveData = MutableLiveData<Result<Quran.Ayah>>()

    val isQuranNotDownloaded: Boolean get() = !spController.isQuranDownloaded


    init {
//        val isQuranDownloaded = spController.isQuranDownloaded
//        quranIsNotDownloaded.value = !isQuranDownloaded
        if (!isQuranNotDownloaded) {
            getAya(ayaNumber)
        }
//        background {
//            val quran = repo.getQuran()
//            main {
//                when(quran) {
//                    is Result.Success -> quranLiveData.value = quran.data
//                    is Result.Error -> Timber.e(quran.exception)
//                }
//            }
//        }
    }

    fun showLoading() {
        _isLoading.value = true
    }

    fun hideLoading() {
        _isLoading.value = false
    }

    fun next() {
        getAya(spController.ayaNumber + 1)
    }

    fun loadChapter(sura: Surah) {
        background {
            val summation = (getAyaSummationFromSura(sura) as? Result.Success)?.data
            summation?.let { getAya(it) }
        }
    }

    private suspend fun getAyaSummationFromSura(surah: Surah): Result<Int> {
        val list = repo.chaptersList()
        return list.map { it.take(surah.number - 1).sumBy { 0 } + 1 }
    }

    private fun getAya(ayaNumber: Int) {
        background(
            onError = {
                ayaLiveData.postValue(Result.Error(IOException(it)))
            }
        ) {
            val aya = repo.getAya(ayaNumber)
            spController.ayaNumber = ayaNumber
            ayaLiveData.postValue(Result.Success(aya))
        }
    }
}