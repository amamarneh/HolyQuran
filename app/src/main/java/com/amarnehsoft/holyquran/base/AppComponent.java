package com.amarnehsoft.holyquran.base;

import com.amarnehsoft.holyquran.db.DBModule;
import com.amarnehsoft.holyquran.di.BuilderModule;
import com.amarnehsoft.holyquran.viewmodel.ViewModelModule;
import com.amarnehsoft.holyquran.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class, AndroidInjectionModule.class, AndroidSupportInjectionModule.class, NetworkModule.class, BuilderModule.class,
        ViewModelModule.class, DBModule.class
})
public interface AppComponent {
    void inject(App app);
}
