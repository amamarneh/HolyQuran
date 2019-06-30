package com.amarnehsoft.holyquran.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AyahEntity(
    @PrimaryKey
    var number: Int = -1,
    var hizbQuarter: Int = -1,
    var juz: Int = -1,
    var manzil: Int = -1,
    var numberInSurah: Int = -1,
    var page: Int = -1,
    var ruku: Int = -1,
    var text: String = "",
    var surahNumber: Int = -1
)