package com.amarnehsoft.holyquran.cache

/**
 * A record used to store cache records or any class need to save a [value]
 * within specific time [timeOut].
 */
class CacheRecord<T> constructor(val value: T, private val timeOut: Long) {
    private val timeStamp: Long = System.currentTimeMillis()

    /**
     * @return true if the timestamp for this record is older than the timeout value.
     */
    val isExpired: Boolean
        get() = timeOut >= 0 && System.currentTimeMillis() - timeStamp >= timeOut
}