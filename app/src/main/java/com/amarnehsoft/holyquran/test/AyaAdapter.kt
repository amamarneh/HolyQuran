package com.amarnehsoft.holyquran.test

import android.media.MediaPlayer
import android.util.SparseArray

import com.amarnehsoft.holyquran.repo.Repo
import com.amarnehsoft.holyquran.utils.Constants

abstract class AyaAdapter protected constructor(
    private val repo: Repo,
    private val initialAyaNumber: Int,
    private val onCompletionListener: MediaPlayer.OnCompletionListener,
    private val withSound: Boolean
) {
    private val maxSize = 5
    private val startIndex = 2
    private val arr: SparseArray<AyaHolder> = SparseArray()

    val first: AyaHolder
        get() {
            return arr.valueAt(0)
        }

    suspend fun init() {
//        for (i in 0 until maxSize) {
//            val aya = getAya(initialAyaNumber + i)
//            if (i + 1 == startIndex) {
//                queueIsInitialized(arr.valueAt(0))
//            }
//        }
    }

//    private suspend fun getAya(ayaNumber: Int): Aya {
//        Timber.w("Amarneh, getAyaAsync:%s", ayaNumber)
//        val aya =
//            arr.get(ayaNumber)?.let { Result.Success(it.aya) } ?: repo.getAya(ayaNumber).ayaResult
//        arr.put(aya.number, AyaHolder(aya, onCompletionListener, withSound))
//        Timber.e("Amarneh, aya ready:%s", aya.number)
//        return aya
//    }

    suspend fun loadNext(): AyaHolder? {
        val firstAyaHolder: AyaHolder?
        try {
            firstAyaHolder = arr.valueAt(0)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        if (firstAyaHolder != null) {
            firstAyaHolder.release()
            arr.removeAt(arr.indexOfValue(firstAyaHolder))

            val number = firstAyaHolder.aya.number + maxSize

            if (arr.get(number) == null && number <= Constants.LAST_AYA_NUMBER) {
//                getAya(number)
            }
            return try {
                arr.valueAt(0)
            } catch (e: ClassCastException) {
                e.printStackTrace()
                null
            }
        } else
            return null
    }

    abstract fun queueIsInitialized(ayaHolder: AyaHolder)
    abstract fun onError(throwable: Throwable)

    fun pause() = try {
        arr.valueAt(0).pause()
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    fun play() = try {
        arr.valueAt(0).play()
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }

    fun release() {
        for (i in 0 until arr.size()) {
            val obj = arr.valueAt(i)
            obj.release()
        }
        arr.clear()
    }
}
