package com.amarnehsoft.holyquran.di

import com.amarnehsoft.holyquran.fragments.chapters.ChaptersFragment
import com.amarnehsoft.holyquran.fragments.verses.VersesFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector
    abstract fun provideChapterFragment(): ChaptersFragment

    @ContributesAndroidInjector
    abstract fun provideVersesFragment(): VersesFragment

}
