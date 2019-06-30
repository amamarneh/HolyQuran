package com.amarnehsoft.holyquran.di

import android.content.Context
import com.amarnehsoft.holyquran.viewmodel.ViewModelFactory
import javax.inject.Inject

class ViewModelFactoryProvider @Inject constructor(context: Context) {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    init {
        AppDependencyInjector.inject(context, this)
    }
}