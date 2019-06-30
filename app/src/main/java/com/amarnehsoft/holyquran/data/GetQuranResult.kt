package com.amarnehsoft.holyquran.data

import com.amarnehsoft.holyquran.model.State
import java.lang.Exception
import java.lang.IllegalStateException

sealed class GetQuranResult : State {

    object FetchingFromNetwork : GetQuranResult()

    object SaveToDatabase : GetQuranResult()

    data class Error(val e: Exception) : GetQuranResult()

    object Success : GetQuranResult()

    override fun exception(): Exception =
        if (this is Error) e
        else throw IllegalStateException("Can't get exception")


    override fun isErrorState(): Boolean {
        return this is Error
    }
}