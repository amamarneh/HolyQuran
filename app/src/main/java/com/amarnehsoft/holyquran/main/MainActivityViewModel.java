package com.amarnehsoft.holyquran.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.amarnehsoft.holyquran.R;
import com.amarnehsoft.holyquran.Repo;
import com.amarnehsoft.holyquran.SPController;
import com.amarnehsoft.holyquran.model.Surah;
import com.amarnehsoft.holyquran.test.AyaAdapter;
import com.amarnehsoft.holyquran.test.AyaHolder;
import com.amarnehsoft.holyquran.utils.ResourceProvider;

import javax.inject.Inject;

import timber.log.Timber;

public class MainActivityViewModel extends ViewModel{

    public MutableLiveData<String> txtSurah = new MutableLiveData<>();
    public MutableLiveData<String> txtJuz = new MutableLiveData<>();
    public MutableLiveData<String> txtAyah = new MutableLiveData<>();
    public MutableLiveData<String> txtTafseer = new MutableLiveData<>();
    public MutableLiveData<String> txtAyahNumber = new MutableLiveData<>();
    public MutableLiveData<Boolean> showTafseer = new MutableLiveData<>();
    public MutableLiveData<Boolean> showTranslation = new MutableLiveData<>();
    public MutableLiveData<String> txtTranslation = new MutableLiveData<>();
    public MutableLiveData<Boolean> showProgress = new MutableLiveData<>();
    public MutableLiveData<Boolean> isPlaying = new MutableLiveData<>();
    public MutableLiveData<Boolean> withSound = new MutableLiveData<>();

    private ResourceProvider resourceProvider;

    private AyaAdapter ayaAdapter;
    private MainPresenter presenter;
    private Repo repo;
    private SPController spController;
    private MutableLiveData<String> liveError = new MutableLiveData<>();

    @Inject
    MainActivityViewModel(Repo repo, SPController spController, ResourceProvider resourceProvider){
        this.resourceProvider = resourceProvider;
        this.repo = repo;
        this.spController = spController;
        //refresh();
    }

    private void newAdapter(int ayaNumber){
        showProgress.postValue(true);
        Timber.w("Amarneh, withSound.getValue() != null && withSound.getValue()=" + (withSound.getValue() != null && withSound.getValue()));
        ayaAdapter = new AyaAdapter(repo, ayaNumber, mediaPlayer -> next(), withSound.getValue() != null && withSound.getValue()) {
            @Override
            public void queueIsInitialized(AyaHolder ayaHolder) {
                updateUI(ayaHolder);
                showProgress.postValue(false);
            }

            @Override
            public void onError(String error) {
                showProgress.postValue(false);
                liveError.postValue(error);
            }
        };
    }

    public void init(int ayaNumber){
        newAdapter(ayaNumber);
    }

    public void refresh(){
        beforeLoadSurah();
        init(spController.getAyaNumber());
    }

    public void beforeLoadSurah(){
        //showProgress.postValue(true);
        txtAyah.postValue("");
        txtAyahNumber.postValue("");
        txtJuz.postValue("");
        txtSurah.postValue("");
        txtTafseer.postValue("");
        txtTranslation.postValue("");
        if (ayaAdapter != null)
            ayaAdapter.release();
    }

    public void init(MainPresenter mainPresenter){
        presenter = mainPresenter;
        refresh();
    }

    public void next(){
        AyaHolder holder = ayaAdapter.loadNext();
        if (holder != null){
            updateUI(holder);
        }
    }

    private void updateUI(AyaHolder ayaHolder){
        txtAyah.postValue(ayaHolder.getAya().getText());
        txtAyahNumber.postValue(String.valueOf(ayaHolder.getAya().getNumberInSurah()));
        txtSurah.postValue(ayaHolder.getAya().getSurah().getName());
        this.txtJuz.postValue(resourceProvider.getString(R.string.juz) + " " + String.valueOf(ayaHolder.getAya().getJuz()));
        txtTafseer.postValue(ayaHolder.getAya().getTafseer());
        txtTranslation.postValue(ayaHolder.getAya().getTranslation());
        if (ayaHolder.play()) isPlaying.postValue(true);
        spController.setAyaNumber(ayaHolder.getAya().getNumber());
    }

    public MutableLiveData<String> getLiveError() {
        return liveError;
    }

    public void updateRadioButtons(int value){
        //0 -> text, 1-> tafseer, 2-> translation
        if (value==0){
            showTafseer.postValue(false);
            showTranslation.postValue(false);
        }else if (value==1){
            showTafseer.postValue(true);
            showTranslation.postValue(false);
        }else if (value==2){
            showTafseer.postValue(false);
            showTranslation.postValue(true);
        }
    }

    private void pauseCommand(){
        boolean pause = ayaAdapter.pause();
        if (pause)
            isPlaying.postValue(false);
    }

    private void playCommand(){
        boolean play = ayaAdapter.play();
        if(play)
            isPlaying.postValue(true);
    }

    public void showChaptersList(){
        presenter.showChaptersList();
    }

    public void showVersesList(){
        presenter.showVersesList();
    }

    public Surah getCurrentSurah() {
        return ayaAdapter.getFirst() != null ? ayaAdapter.getFirst().getAya() != null ? ayaAdapter.getFirst().getAya().getSurah() : null : null;
    }

    public void toggleSound() {
        withSound.postValue(withSound.getValue() == null || !withSound.getValue());
    }

    public void togglePlay(){
        if (isPlaying.getValue() != null && isPlaying.getValue()){
            pauseCommand();
        }else {
            playCommand();
        }
    }

    public void prev(){
        if (ayaAdapter.getFirst() != null && ayaAdapter.getFirst().getAya().getNumber() > 1){
            int ayaNumber = ayaAdapter.getFirst().getAya().getNumber()-1;
            beforeLoadSurah();
            init(ayaNumber);
        }
    }
}
