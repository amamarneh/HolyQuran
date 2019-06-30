package com.amarnehsoft.holyquran.test

import android.media.MediaPlayer

import com.amarnehsoft.holyquran.main.readersFragment.ReaderController
import com.amarnehsoft.holyquran.model.Aya

import java.io.IOException

class AyaHolder(
    val aya: Aya,
    onCompletionListener: MediaPlayer.OnCompletionListener,
    withSound: Boolean
) {

    private var mediaPlayer: MediaPlayer? = null

    init {
        if (withSound) {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setOnCompletionListener(onCompletionListener)
            try {
                mediaPlayer?.setDataSource(getSoundUrl(aya.number))
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                mediaPlayer?.prepare()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getSoundUrl(ayaNumber: Int): String {
        return "http://cdn.alquran.cloud/media/audio/ayah/" + ReaderController.currentReader.id + "/" + ayaNumber
    }

    fun release() = mediaPlayer?.release()

    fun play() = mediaPlayer?.start()?.let { true } ?: false

    fun pause() = mediaPlayer?.pause()?.let { true } ?: false
}
