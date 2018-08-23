package com.amarnehsoft.holyquran.di;

import com.amarnehsoft.holyquran.fragments.chapters.ChaptersFragment;
import com.amarnehsoft.holyquran.fragments.verses.VersesFragment;
import com.amarnehsoft.holyquran.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuilderModule {

    @ContributesAndroidInjector
    public abstract MainActivity provideMainActivity();

    @ContributesAndroidInjector
    public abstract ChaptersFragment provideChapterFragment();

    @ContributesAndroidInjector
    public abstract VersesFragment provideVersesFragment();


}
