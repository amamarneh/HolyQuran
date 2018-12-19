package com.amarnehsoft.holyquran.main;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.amarnehsoft.holyquran.R;
import com.amarnehsoft.holyquran.Repo;
import com.amarnehsoft.holyquran.base.InjectedActivity;
import com.amarnehsoft.holyquran.databinding.ActivityMainBinding;
import com.amarnehsoft.holyquran.fragments.chapters.ChaptersFragment;
import com.amarnehsoft.holyquran.fragments.verses.VersesFragment;
import com.amarnehsoft.holyquran.main.readersFragment.ReadersFragment;
import com.amarnehsoft.holyquran.model.Surah;
import com.amarnehsoft.holyquran.test.Reader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends InjectedActivity implements MainPresenter, ChaptersFragment.OnFragmentInteractionListener
, VersesFragment.OnFragmentInteractionListener, ReadersFragment.OnFragmentInteractionListener {



    @Inject
    ViewModelProvider.Factory factory;
    private MainActivityViewModel viewModel;

    @Inject
    Repo repo;

    public static Intent newIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this, factory).get(MainActivityViewModel.class);
        viewModel.init(this);

        //FULL SCREEN
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);

        viewModel.getLiveError().observe(this, s -> {
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG ).show();
            Timber.e("Amarneh, observeError:%s", s);
        });

        viewModel.withSound.observe(this, aBoolean -> {
            viewModel.refresh();
        });

        viewModel.showTafseer.observe(this, aBoolean -> Timber.w("Amarneh, observe showTafseer="+aBoolean));

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    viewModel.updateRadioButtons(0);
                }else if (tab.getPosition() == 1){
                    viewModel.updateRadioButtons(1);
                }else if (tab.getPosition() == 2){
                    viewModel.updateRadioButtons(2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        Picasso.with(this)
//                .load(R.drawable.bg1)
//                .into(binding.mainImg);
        Glide.with(this)
                .load(R.drawable.bg1)
                .transition(DrawableTransitionOptions.withCrossFade(1000))
                .into(binding.mainImg);


    }

    @Override
    public void showChaptersList() {
        ChaptersFragment.newInstance().show(getSupportFragmentManager(), ChaptersFragment.TAG);
    }

    @Override
    public void showVersesList() {
        if (viewModel.getCurrentSurah() != null) {
            viewModel.showProgress.postValue(true);
            getAyaNumber(viewModel.getCurrentSurah())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Integer>() {
                        @Override
                        public void onSuccess(Integer integer) {
                            viewModel.showProgress.postValue(false);
                            VersesFragment.newInstance(integer ,viewModel.getCurrentSurah().getNumberOfAyahs()).show(getSupportFragmentManager(), VersesFragment.TAG);
                        }

                        @Override
                        public void onError(Throwable e) {
                            viewModel.showProgress.postValue(false);
                            viewModel.getLiveError().postValue(e.getMessage());
                        }
                    });
        }
    }

    @Override
    public void chapterSelected(Surah surah) {
        viewModel.beforeLoadSurah();
        getAyaNumber(surah).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        viewModel.init(integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        viewModel.getLiveError().postValue(e.getMessage());
                    }
                });
    }

    @Override
    public void ayaSelected(int ayaNumber) {
        viewModel.beforeLoadSurah();
        viewModel.init(ayaNumber);
    }

    private Single<Integer> getAyaNumber(Surah surah){
        return repo.getChaptersList()
                .map(list -> {
                    int ayaNumber=0;
                    for (int i=1; i < surah.getNumber(); i++){
                        ayaNumber += list.get(i-1).getNumberOfAyahs();
                    }
                    ayaNumber = ayaNumber+1;
                    return ayaNumber;
                });
    }

    @Override
    public void resetAndPlay(Reader reader) {
        viewModel.refresh();
    }
}
