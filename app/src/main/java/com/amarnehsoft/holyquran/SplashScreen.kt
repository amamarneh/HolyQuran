package com.amarnehsoft.holyquran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import com.amarnehsoft.holyquran.main.MainActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed({
            startActivity(MainActivity.newIntent(this))
            finish()
        }, 300)
    }
}
