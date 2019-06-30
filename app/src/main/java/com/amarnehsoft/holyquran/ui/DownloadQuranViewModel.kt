package com.amarnehsoft.holyquran.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.amarnehsoft.holyquran.R
import com.amarnehsoft.holyquran.data.GetQuranResult
import com.amarnehsoft.holyquran.domain.GetQuranUseCase
import com.amarnehsoft.holyquran.utils.CoroutinesDispatcherProvider
import com.amarnehsoft.holyquran.utils.ResourceProvider
import com.amarnehsoft.holyquran.viewmodel.BaseViewModel
import javax.inject.Inject

class DownloadQuranViewModel @Inject constructor(
    dispatcherProvider: CoroutinesDispatcherProvider,
    private val getQuranUseCase: GetQuranUseCase,
    private val resourcesProvider: ResourceProvider
) : BaseViewModel(dispatcherProvider) {

    private val _quranLiveData = MediatorLiveData<GetQuranResult>()
    val quranLiveData: LiveData<GetQuranResult> = _quranLiveData

    private val _showLoading = MediatorLiveData<Boolean>()
    val showLoading: LiveData<Boolean> get() = _showLoading

    val stateLiveData = Transformations.map(_quranLiveData) {
        when (it) {
            is GetQuranResult.FetchingFromNetwork -> resourcesProvider.getString(R.string.fetching_from_network)
            is GetQuranResult.SaveToDatabase -> resourcesProvider.getString(R.string.save_to_database)
            is GetQuranResult.Success -> resourcesProvider.getString(R.string.downloaded_successfully)
            is GetQuranResult.Error -> resourcesProvider.getString(R.string.something_went_wrong)
        }
    }

    init {
        _showLoading.addSource(_quranLiveData) {
            if (it == GetQuranResult.Success || it is GetQuranResult.Error) {
                _showLoading.value = false
            }
        }

        getQuran()
    }

    private fun getQuran() {
        _showLoading.value = true
        _quranLiveData.addSource(getQuranUseCase()) {
            _quranLiveData.value = it
        }
    }
}