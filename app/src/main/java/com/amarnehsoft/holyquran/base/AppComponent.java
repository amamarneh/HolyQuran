package com.amarnehsoft.holyquran.base;

import com.amarnehsoft.holyquran.di.BuilderModule;
import com.amarnehsoft.holyquran.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class, AndroidInjectionModule.class, AndroidSupportInjectionModule.class, NetworkModule.class, BuilderModule.class
})
public interface AppComponent {
    void inject(App app);
}
