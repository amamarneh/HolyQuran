package com.amarnehsoft.holyquran.model

import java.lang.IllegalStateException

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> : State {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

    override fun exception(): java.lang.Exception =
        if (this is Error) this.exception
        else throw IllegalStateException("")

    override fun isErrorState(): Boolean = this is Error

    fun <RESULT : Any> map(mapper: (T) -> RESULT): Result<RESULT> {
        return when (this) {
            is Success -> Success(mapper(data))
            is Error -> this
        }
    }
}