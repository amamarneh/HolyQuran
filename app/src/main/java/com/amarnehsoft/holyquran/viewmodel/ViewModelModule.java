package com.amarnehsoft.holyquran.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.amarnehsoft.holyquran.fragments.chapters.ChaptersFragmentViewModel;
import com.amarnehsoft.holyquran.fragments.verses.VersesFragmentViewModel;
import com.amarnehsoft.holyquran.main.MainActivityViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory provideViewModelFactory(ViewModelFactory factory);


    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindUserViewModel(MainActivityViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ChaptersFragmentViewModel.class)
    abstract ViewModel bindsChaptersViewModel(ChaptersFragmentViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VersesFragmentViewModel.class)
    abstract ViewModel bindsVersesFragmentViewModel(VersesFragmentViewModel viewModel);
}
