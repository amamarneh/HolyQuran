package com.amarnehsoft.holyquran.di;

import com.amarnehsoft.holyquran.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuilderModule {

    @ContributesAndroidInjector
    public abstract MainActivity provideMainActivity();

}
