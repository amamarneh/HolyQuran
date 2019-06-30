package com.amarnehsoft.holyquran.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.amarnehsoft.holyquran.fragments.chapters.ChaptersFragmentViewModel
import com.amarnehsoft.holyquran.fragments.verses.VersesFragmentViewModel
import com.amarnehsoft.holyquran.main.TestViewModel
import com.amarnehsoft.holyquran.ui.DownloadQuranViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@SuppressWarnings("unused")
@Module
abstract class ViewModelsModule {

    @Binds
    abstract fun provideViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ChaptersFragmentViewModel::class)
    abstract fun bindsChaptersViewModel(viewModel: ChaptersFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(VersesFragmentViewModel::class)
    abstract fun bindsVersesFragmentViewModel(viewModel: VersesFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TestViewModel::class)
    abstract fun testViewModel(viewModel: TestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DownloadQuranViewModel::class)
    abstract fun downloadQuranViewModel(viewModel: DownloadQuranViewModel): ViewModel
}
