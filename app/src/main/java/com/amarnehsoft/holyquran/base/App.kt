package com.amarnehsoft.holyquran.base

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment

import com.amarnehsoft.holyquran.BuildConfig
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import javax.inject.Inject

import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber

class App : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var dispatchingFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var activityLifecycleCallbacks: ActivityLifecycleCallbacks
    val appComponent: AppComponent by lazy { createAppComponent() }

    override fun onCreate() {
        super.onCreate()
        setupDagger()
        setupTimber()
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    override fun activityInjector() = dispatchingActivityInjector

    override fun supportFragmentInjector() = dispatchingFragmentInjector

    private fun setupDagger() = appComponent.inject(this)

    private fun createAppComponent(): AppComponent =
        DaggerAppComponent.builder().application(this).build()

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Fabric.with(this, Crashlytics())
        }
    }
}
