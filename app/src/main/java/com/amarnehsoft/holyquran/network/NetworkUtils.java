package com.amarnehsoft.holyquran.network;

import android.content.Context;
import android.net.ConnectivityManager;

import com.amarnehsoft.holyquran.base.App;

import javax.inject.Inject;

public class NetworkUtils {
    private App app;

    @Inject
    public NetworkUtils(App app){
        this.app = app;
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager cm = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
