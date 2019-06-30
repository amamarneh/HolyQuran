package com.amarnehsoft.holyquran.base

import com.amarnehsoft.holyquran.db.DBModule
import com.amarnehsoft.holyquran.di.BuilderModule
import com.amarnehsoft.holyquran.di.ViewModelFactoryProvider
import com.amarnehsoft.holyquran.viewmodel.ViewModelsModule
import com.amarnehsoft.holyquran.network.NetworkModule
import dagger.BindsInstance

import javax.inject.Singleton

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(
    modules = [AppModule::class,
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        BuilderModule::class,
        ViewModelsModule::class,
        DBModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
    fun inject(viewModelFactoryProvider: ViewModelFactoryProvider)
}
