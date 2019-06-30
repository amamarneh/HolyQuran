package com.amarnehsoft.holyquran.utils

import androidx.annotation.Nullable

/**
 * A wrapper for a nullable values, used to avoid emitting null values when dealing with RxJava2,
 * instead, we emit an Optional that holds the nullable value.
 */
class Optional<M>(@Nullable private val optional: M?) {

    val isEmpty: Boolean
        get() = this.optional == null

    val isNotEmpty: Boolean
        get() = this.optional != null

    fun get(): M {
        if (optional == null) {
            throw NoSuchElementException("No value present")
        }
        return optional
    }

    fun getNullable(): M? {
        return optional
    }
}