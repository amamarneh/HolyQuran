package com.amarnehsoft.holyquran.main

import com.amarnehsoft.holyquran.model.Result

class DefaultResultHandlingStrategy<T : Any>(
    private val onSuccess: (T) -> Unit,
    private val onError: () -> Unit
) : ResultHandlingStrategy<T> {

    override fun handleResult(result: Result<T>) {
        when (result) {
            is Result.Success -> onSuccess(result.data)
            is Result.Error -> onError()
        }
    }
}