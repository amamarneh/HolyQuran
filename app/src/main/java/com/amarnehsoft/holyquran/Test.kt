package com.amarnehsoft.holyquran

import android.annotation.SuppressLint
import android.util.Log
import kotlinx.coroutines.*

class Test {
    fun test() {
        print("before launch")
        GlobalScope.launch (Dispatchers.Main) {
            launch { print(getStr1()) }
            launch { print(getStr0_5()) }
            launch { print("some str") }
        }
    }

    suspend fun getStr1(): String {
        delay(1000)
        return "some string after 1 second"
    }

    suspend fun getStr0_5(): String {
        delay(500)
        return "some string after half second"
    }

    @SuppressLint("LogNotTimber")
    private fun print(str: String) {
        Log.d(
            "coroutines",
            "[$str] currentThread: ${Thread.currentThread().name}, currentTime: ${System.currentTimeMillis()}"
        )
    }
}