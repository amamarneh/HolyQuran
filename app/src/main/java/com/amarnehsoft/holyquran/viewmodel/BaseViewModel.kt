package com.amarnehsoft.holyquran.viewmodel

import androidx.lifecycle.ViewModel
import com.amarnehsoft.holyquran.utils.CoroutinesDispatcherProvider
import kotlinx.coroutines.*

abstract class BaseViewModel(private val dispatcherProvider: CoroutinesDispatcherProvider) :
    ViewModel() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    protected fun background(
        onError: suspend (Throwable) -> Unit = { throw it },
        block: suspend () -> Unit
    ): Job = uiScope.launch(dispatcherProvider.computation) {
        try {
            block()
        } catch (error: Throwable) {
            onError(error)
        }
    }

    protected suspend fun main(block: suspend () -> Unit) {
        uiScope.launch(dispatcherProvider.main) { block() }
//        withContext(dispatcherProvider.main) {
//            block()
//        }
    }
}