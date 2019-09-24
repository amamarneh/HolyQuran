package com.amarnehsoft.holyquran.main

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.amarnehsoft.holyquran.R
import com.amarnehsoft.holyquran.databinding.ActivityMainBinding
import com.amarnehsoft.holyquran.extensions.viewModel
import com.amarnehsoft.holyquran.fragments.chapters.ChaptersFragment
import com.amarnehsoft.holyquran.fragments.verses.VersesFragment
import com.amarnehsoft.holyquran.main.readersFragment.ReadersFragment
import com.amarnehsoft.holyquran.model.Result
import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.test.Reader
import com.amarnehsoft.holyquran.ui.DownloadQuranFragment

class MainActivity : AppCompatActivity(),
    ChaptersFragment.OnFragmentInteractionListener, VersesFragment.OnFragmentInteractionListener,
    ReadersFragment.OnFragmentInteractionListener {

    override fun chapterSelected(surah: Surah) {
        //viewModel.loadChapter(surah)
    }

    private val viewModel by lazy { viewModel<TestViewModel>() }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        initViewModel()

//        viewModel.binding.withSound.observe(this, Observer { viewModel.refresh() })

//        viewModel.navigationLiveData.observe(this, Observer {
//            @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
//            when (it) {
//                MainActivityNavigation.SHOW_CHAPTERS_LIST -> showChaptersList()
//                MainActivityNavigation.SHOW_VERSES_LIST -> showVersesList()
//            }
//        })

//        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                when {
//                    tab.position == 0 -> viewModel.updateRadioButtons(0)
//                    tab.position == 1 -> viewModel.updateRadioButtons(1)
//                    tab.position == 2 -> viewModel.updateRadioButtons(2)
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab) {
//
//            }
//        })
    }

    private fun initView() {
        binding.lifecycleOwner = this
        binding.btnNext.setOnClickListener { viewModel.next() }
        binding.txtSurah.setOnClickListener { showChaptersList() }
        binding.txtNumberInSurah.setOnClickListener { showVersesList() }
        if (viewModel.isQuranNotDownloaded) {
            showDownloadingQuranLayout()
        }
    }

    private fun initViewModel() {
        binding.viewmodel = viewModel
        viewModel.ayaLiveData.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    binding.aya.text = it.data.text
                }
                is Result.Error -> {
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showDownloadingQuranLayout() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, DownloadQuranFragment())
            .commit()
    }

    private fun showChaptersList() {
        ChaptersFragment.newInstance().show(supportFragmentManager, ChaptersFragment.TAG)
    }

    private fun showVersesList() {
//        viewModel.launchVersesFragment().observe(this, Observer { ayaSummation ->
//            VersesFragment.newInstance(
//                ayaSummation, viewModel.currentSurah?.numberOfAyahs ?: 0
//            ).show(supportFragmentManager, VersesFragment.TAG)
//        })
    }

    override fun ayaSelected(ayaNumber: Int) {
//        viewModel.beforeLoadSurah()
//        viewModel.reloadFromAya(ayaNumber)
    }

    override fun resetAndPlay(reader: Reader) {
//        viewModel.refresh()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}

/**
 *
 * - Quran Not Downloaded -> show download quran layout.
 * else show the last read aya.
 *
 *
 */
