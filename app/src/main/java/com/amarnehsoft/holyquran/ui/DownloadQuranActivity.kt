package com.amarnehsoft.holyquran.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amarnehsoft.holyquran.R

class DownloadQuranActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_quran)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, DownloadQuranActivity::class.java)
        }
    }
}