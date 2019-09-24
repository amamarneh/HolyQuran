package com.amarnehsoft.holyquran.cache

open class ExpireableMutableMap<KEY, DATA>(
        private val map: MutableMap<KEY, CacheRecord<DATA>>,
        private val defaultTimeOut: Long,
        private val strategy: CacheValueStrategy<KEY, DATA> = DefaultCacheValueStrategy()
): MutableMap<KEY, CacheRecord<DATA>> by map {

    override fun get(key: KEY): CacheRecord<DATA>? {
        return strategy.handleValue(key, map[key])
    }

    operator fun set(key: KEY, value: DATA) {
        map[key] = CacheRecord(value, defaultTimeOut)
    }

    fun set(key: KEY, value: DATA, timeOut: Long) {
        map[key] = CacheRecord(value, timeOut)
    }

    fun toExpirableMap(): ExpirableMap<KEY, DATA> {
        return ExpirableMap(map, strategy)
    }
}