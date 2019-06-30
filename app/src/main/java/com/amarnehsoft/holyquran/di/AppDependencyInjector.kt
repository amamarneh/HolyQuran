package com.amarnehsoft.holyquran.di

import android.content.Context
import com.amarnehsoft.holyquran.base.App

object AppDependencyInjector {

    fun inject(context: Context, viewModelFactoryProvider: ViewModelFactoryProvider) {
        (context.applicationContext as App).appComponent.inject(viewModelFactoryProvider)
    }
}