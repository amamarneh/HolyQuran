package com.amarnehsoft.holyquran.model

import com.amarnehsoft.holyquran.network.quran.Quran

data class Aya(
    var number: Int,
    var text: String,
    var numberInSurah: Int,
    var juz: Int,
    var manzil: Int,
    var page: Int,
    var ruku: Int,
    var hizbQuarter: Int
//    var surahNumber: Int
    //var audio: String,
    //var tafseer: String = "",
    //var translation: String = "",
    //var sajda: Any? = null,
//    var surah: Surah
) {
    companion object {
        fun create(apiAya: Quran.Ayah): Aya {
            return Aya(
                number = apiAya.number,
                text = apiAya.text,
                numberInSurah = apiAya.numberInSurah,
                juz = apiAya.juz,
                manzil = apiAya.manzil,
                page = apiAya.page,
                ruku = apiAya.ruku,
                hizbQuarter = apiAya.hizbQuarter
            )
        }
    }
}
