package com.amarnehsoft.holyquran.utils

import android.content.Context
import android.net.ConnectivityManager
import com.amarnehsoft.holyquran.base.App
import com.amarnehsoft.holyquran.model.Result
import java.io.IOException
import javax.inject.Inject

class NetworkUtils @Inject constructor(private val app: App) {

    val isConnectedToInternet: Boolean
        get() {
            val cm = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            return cm != null && cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
        }
}


/**
 * Wrap a suspending API [call] in try/catch. In case an exception is thrown, a [Result.Error] is
 * created based on the [errorMessage].
 */
suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
    return try {
        call()
    } catch (e: Exception) {
        // An exception was thrown when calling the API so we're converting this to an IOException
        Result.Error(IOException(errorMessage, e))
    }
}