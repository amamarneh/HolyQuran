package com.amarnehsoft.holyquran.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.amarnehsoft.holyquran.utils.CoroutinesDispatcherProvider

import com.amarnehsoft.holyquran.utils.ResourceProvider

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class AppModule {

    @Provides
    fun provideContext(app: App): Context {
        return app
    }

    @Provides
    fun provideSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideResourceProvider(context: Context): ResourceProvider {
        return ResourceProvider(context)
    }

    @Provides
    fun provideActivityLifecycleCallbacks(activityLifecycleCallbacks: ActivityLifecycleCallbacksImpl): Application.ActivityLifecycleCallbacks =
        activityLifecycleCallbacks

    @Provides
    fun provideCoroutinesDispatcherProvider() = CoroutinesDispatcherProvider(
        main = Dispatchers.Main,
        computation = Dispatchers.Default,
        io = Dispatchers.IO
    )
}
