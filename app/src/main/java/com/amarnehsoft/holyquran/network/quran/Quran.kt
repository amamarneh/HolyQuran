package com.amarnehsoft.holyquran.network.quran

/**
 * Json representation for get quran api.
 */
data class Quran(
    val edition: Edition,
    val surahs: List<Surah>
) {
    data class Surah(
        var number: Int = -1,
        var englishName: String = "",
        var englishNameTranslation: String = "",
        var name: String = "",
        var revelationType: String = "",
        var ayahs: List<Ayah> = emptyList()
    )

    data class Ayah(
        val hizbQuarter: Int,
        val juz: Int,
        val manzil: Int,
        val number: Int,
        val numberInSurah: Int,
        val page: Int,
        val ruku: Int,
        val text: String
    )

    data class Edition(
        val englishName: String,
        val format: String,
        val identifier: String,
        val language: String,
        val name: String,
        val type: String
    )
}