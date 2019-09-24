package com.amarnehsoft.holyquran.cache

import com.amarnehsoft.holyquran.network.quran.Quran

class QuranCache(private val cache: ExpirableMap<Int, Quran>) {

}