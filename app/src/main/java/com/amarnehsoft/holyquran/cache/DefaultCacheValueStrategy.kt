package com.amarnehsoft.holyquran.cache

class DefaultCacheValueStrategy<KEY, DATA>: CacheValueStrategy<KEY, DATA> {
    override fun handleValue(key: KEY, value: CacheRecord<DATA>?): CacheRecord<DATA>? {
        return value
    }
}