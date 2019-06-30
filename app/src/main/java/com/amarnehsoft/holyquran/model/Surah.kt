package com.amarnehsoft.holyquran.model

data class Surah(
    var number: Int,
    var name: String,
    var englishName: String,
    var englishNameTranslation: String,
    var numberOfAyahs: Int,
    var revelationType: String
)
