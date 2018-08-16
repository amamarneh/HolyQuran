package com.amarnehsoft.holyquran;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amarnehsoft.holyquran.base.InjectedActivity;
import com.amarnehsoft.holyquran.model.Aya;
import com.amarnehsoft.holyquran.model.TafseerAyah;

import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends InjectedActivity {

    @Inject
    Repo repo;

    @Inject
    SPController spController;

    private int number = 1;

    private Aya nextAya, currentAya;
    private TafseerAyah nextTafsser, currentTafseer;
    private TextView txtView;
    private Button btnNext;
    private TextView txtBottom, txtNumberInSurah, txtSurah, txtJuz, txtTafseer;

    private boolean firstLunch = true;
    private boolean firstLunchTafseer = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = findViewById(R.id.txtView);
        btnNext = findViewById(R.id.btnNext);
        txtBottom = findViewById(R.id.txtBottom);
        txtNumberInSurah = findViewById(R.id.txtNumberInSurah);
        txtSurah = findViewById(R.id.txtSurah);
        txtJuz = findViewById(R.id.txtJuz);
        txtTafseer = findViewById(R.id.txtTafseer);

        findViewById(R.id.ayaLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTafseer.setVisibility(txtTafseer.getVisibility()==View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        });

        number = spController.getAyaNumber()-1;
        if (number < 1)
            number = 1;

        fetchAyah();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentAya = nextAya;
                currentTafseer = nextTafsser;
                updateUI();
                updateTafseerUI();
                txtBottom.setText("");
                number++;
                fetchAyah();
            }
        });
    }

    private void updateUI(){
        txtView.setText(currentAya.getText());
        txtNumberInSurah.setText(String.valueOf(currentAya.getNumberInSurah()));
        txtSurah.setText(currentAya.getSurah().getName());
        txtJuz.setText(getString(R.string.juz) + " " + String.valueOf(currentAya.getJuz()));
    }

    private void updateTafseerUI(){
        txtTafseer.setText(currentTafseer != null ? currentTafseer.getText() : "");
    }

    private void fetchAyah(){
        final int oldNumber = number;
        repo.getAya(number).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Aya>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Aya aya) {
                        if (oldNumber == number){
                            nextAya = aya;
                            txtBottom.setText(aya.getText());
                            if (firstLunch){
                                firstLunch=false;
                                currentAya = nextAya;
                                updateUI();
                                txtBottom.setText("");
                                number++;
                                fetchAyah();
                            }

                            repo.tafseerAyah(aya)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new SingleObserver<TafseerAyah>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onSuccess(TafseerAyah tafseerAyah) {
                                            if (oldNumber == number){
                                                nextTafsser = tafseerAyah;
                                                if (firstLunchTafseer){
                                                    firstLunchTafseer =false;
                                                    currentTafseer = tafseerAyah;
                                                    updateTafseerUI();
                                                }
                                            }else if (oldNumber == number-1){
                                                currentTafseer = tafseerAyah;
                                                updateTafseerUI();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            txtTafseer.setText(e.getMessage());
                                        }
                                    });
                        }else if (oldNumber == number-1){
                            currentAya = aya;
                            updateUI();
                            //fetchAyah();
                        }
                        spController.setAyaNumber(number);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }
}
