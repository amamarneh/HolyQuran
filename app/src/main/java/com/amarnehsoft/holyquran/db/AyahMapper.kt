package com.amarnehsoft.holyquran.db

import com.amarnehsoft.holyquran.network.quran.Quran

object AyahMapper {
    fun map(ayah: Quran.Ayah, surahNumber: Int) = AyahEntity(
        number = ayah.number,
        hizbQuarter = ayah.hizbQuarter,
        juz = ayah.juz,
        manzil = ayah.manzil,
        page = ayah.page,
        numberInSurah = ayah.numberInSurah,
        ruku = ayah.ruku,
        text = ayah.text,
        surahNumber = surahNumber
    )

    fun map(ayah: AyahEntity) = Quran.Ayah(
        number = ayah.number,
        hizbQuarter = ayah.hizbQuarter,
        juz = ayah.juz,
        manzil = ayah.manzil,
        page = ayah.page,
        numberInSurah = ayah.numberInSurah,
        ruku = ayah.ruku,
        text = ayah.text
    )
}