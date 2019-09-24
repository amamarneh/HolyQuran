package com.amarnehsoft.holyquran.cache

class Client {


    fun useCache() {

        val strategy = RemoveExpiredValuesStrategy<Int, String>()
        val mutableCache = ExpireableMutableMap(HashMap(),1000, strategy)
        strategy.map = mutableCache

        mutableCache.set(1, "one", 100)
        mutableCache.set(2, "two", 200)
        mutableCache.remove(2)

        val record = mutableCache[1]?.value


        val cache = ExpirableMap<Int, String>(HashMap())
        val b = cache[1]
    }
}