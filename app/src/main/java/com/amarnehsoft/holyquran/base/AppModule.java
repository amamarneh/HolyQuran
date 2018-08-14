package com.amarnehsoft.holyquran.base;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private App app;

    public AppModule(App app){
        this.app = app;
    }

    @Provides
    public Context provideContext(){
        return app;
    }

    @Provides
    public App provideApp(){
        return app;
    }

    @Provides
    SharedPreferences provideSharedPrefs(Context context){
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }
}
