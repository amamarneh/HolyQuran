package com.amarnehsoft.holyquran;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class SPController {
    private SharedPreferences sharedPreferences;

    @Inject
    public SPController(SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
    }

    public void setAyaNumber(int number){
        sharedPreferences.edit().putInt("ayaNumber", number).apply();
    }

    public int getAyaNumber(){
        return sharedPreferences.getInt("ayaNumber", 1);
    }
}
