package com.amarnehsoft.holyquran.main

import androidx.lifecycle.MutableLiveData

class MainActivityBinding {

    var txtSurah = MutableLiveData<String>()
    var txtJuz = MutableLiveData<String>()
    var txtAyah = MutableLiveData<String>()
    var txtTafseer = MutableLiveData<String>()
    var txtAyahNumber = MutableLiveData<String>()
    var showTafseer = MutableLiveData<Boolean>()
    var showTranslation = MutableLiveData<Boolean>()
    var txtTranslation = MutableLiveData<String>()
    var showProgress = MutableLiveData<Boolean>()
    var isPlaying = MutableLiveData<Boolean>()
    var withSound = MutableLiveData<Boolean>()

    fun clear() {
        txtAyah.value = ""
        txtAyahNumber.value = ""
        txtJuz.value = ""
        txtSurah.value = ""
        txtTafseer.value = ""
        txtTranslation.value = ""
    }
}