package com.amarnehsoft.holyquran;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.amarnehsoft.holyquran.main.MainActivity;

public class SplashScreen extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(()->{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        },300);
    }
}
