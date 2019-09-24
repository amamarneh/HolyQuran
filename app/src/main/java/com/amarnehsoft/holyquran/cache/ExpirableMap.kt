package com.amarnehsoft.holyquran.cache

/**
 * A cache manager that uses memory to store cache records.
 * Unlike diskCache which uses LRU and CacheController which uses SharedPreferences.
 * this cache doesn't have a maximum size and doesn't delete records unless
 * they're deleted by calling the [ExpirableMap.deleteRecord] or
 * [ExpirableMap.clear] methods.
 */
class ExpirableMap<KEY, DATA>(
        private val map: Map<KEY, CacheRecord<DATA>>,
        private val strategy: CacheValueStrategy<KEY, DATA> = DefaultCacheValueStrategy()
) : Map<KEY, CacheRecord<DATA>> by map {

    override fun get(key: KEY): CacheRecord<DATA>? {
        return strategy.handleValue(key, map[key])
    }
}
