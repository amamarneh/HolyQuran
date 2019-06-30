package com.amarnehsoft.holyquran

import android.content.SharedPreferences

import javax.inject.Inject

class SPController @Inject
constructor(private val sharedPreferences: SharedPreferences) {

    var ayaNumber: Int
        get() = sharedPreferences.getInt(AYA_NUMBER, 1)
        set(number) = sharedPreferences.edit().putInt(AYA_NUMBER, number).apply()

    var isQuranDownloaded: Boolean
        get() = sharedPreferences.getBoolean(IS_QURAN_DOWNLOADED, false)
        set(value) = sharedPreferences.edit().putBoolean(IS_QURAN_DOWNLOADED, value).apply()

    companion object {
        private const val AYA_NUMBER = "ayaNumber"
        private const val IS_QURAN_DOWNLOADED = "isQuranDownloaded"
    }
}
