package com.amarnehsoft.holyquran.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.amarnehsoft.holyquran.ui.DownloadQuranActivity
import com.amarnehsoft.holyquran.ui.DownloadQuranViewModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(DownloadQuranActivity.newIntent(this))
        finish()
    }
}
