package com.amarnehsoft.holyquran.main

import com.amarnehsoft.holyquran.model.Result

interface ResultHandlingStrategy<T: Any> {
    fun handleResult(result: Result<T>)
}