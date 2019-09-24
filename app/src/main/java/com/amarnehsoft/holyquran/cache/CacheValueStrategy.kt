package com.amarnehsoft.holyquran.cache

interface CacheValueStrategy<K, T> {
    fun handleValue(key: K, value: CacheRecord<T>?): CacheRecord<T>?
}