package com.amarnehsoft.holyquran.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.amarnehsoft.holyquran.model.Result

abstract class ResultHandlingObserver<T : Any> : Observer<Result<T>> {

    override fun onChanged(t: Result<T>?) {
        t?.let {
            resultHandlingStrategy().handleResult(t)
        }
    }

    abstract fun resultHandlingStrategy(): ResultHandlingStrategy<T>
}

fun <T : Any> LiveData<Result<T>>.observeResult(
    lifecycleOwner: LifecycleOwner,
    resultHandlingStrategy: ResultHandlingStrategy<T>
) {
    observe(lifecycleOwner, object : ResultHandlingObserver<T>() {
        override fun resultHandlingStrategy(): ResultHandlingStrategy<T> {
            return resultHandlingStrategy
        }
    })
}