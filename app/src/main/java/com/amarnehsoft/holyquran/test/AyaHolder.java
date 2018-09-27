package com.amarnehsoft.holyquran.test;

import android.media.MediaPlayer;

import com.amarnehsoft.holyquran.main.readersFragment.ReaderController;
import com.amarnehsoft.holyquran.model.Aya;

import java.io.IOException;

public class AyaHolder {

    private MediaPlayer mediaPlayer;
    private Aya aya;

    public AyaHolder(Aya aya, MediaPlayer.OnCompletionListener onCompletionListener, boolean withSound){
        this.aya = aya;
        if (withSound){
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(onCompletionListener);
            try {
                mediaPlayer.setDataSource(getSoundUrl(aya.getNumber()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getSoundUrl(int ayaNumber){
        return "http://cdn.alquran.cloud/media/audio/ayah/" + ReaderController.getCurrentReader().getId() + "/" + ayaNumber;
    }

    public Aya getAya() {
        return aya;
    }

    public void release(){
        if (mediaPlayer != null)
            mediaPlayer.release();
    }

    public boolean play() {
        if (mediaPlayer != null){
            mediaPlayer.start();
            return true;
        }
        return false;

    }

    public boolean pause(){
        if (mediaPlayer != null){
            mediaPlayer.pause();
            return true;
        }
        return false;

    }
}
