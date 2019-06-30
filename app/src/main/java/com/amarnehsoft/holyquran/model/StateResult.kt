package com.amarnehsoft.holyquran.model

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class StateResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : StateResult<T>()
    data class Error(val exception: Exception) : StateResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }

    fun <RESULT : Any> map(mapper: (T) -> RESULT): StateResult<RESULT> {
        return when (this) {
            is Success -> Success(mapper(data))
            is Error -> this
        }
    }
}