package com.amarnehsoft.holyquran.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SuraEntity(
    @PrimaryKey
    var number: Int = -1,
    var englishName: String = "",
    var englishNameTranslation: String = "",
    var name: String = "",
    var revelationType: String = ""
) {
    constructor() : this(-1, "", "", "", "")
}