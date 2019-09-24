package com.amarnehsoft.holyquran.cache

class RemoveExpiredValuesStrategy<KEY, T> : CacheValueStrategy<KEY, T> {

    lateinit var map: ExpireableMutableMap<KEY, T>

    override fun handleValue(key: KEY, value: CacheRecord<T>?): CacheRecord<T>? {
        if (value?.isExpired == true) {
            map.remove(key)
            return null
        }

        return value
    }
}