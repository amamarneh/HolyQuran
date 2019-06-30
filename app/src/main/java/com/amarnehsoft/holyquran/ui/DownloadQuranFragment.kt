package com.amarnehsoft.holyquran.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.amarnehsoft.holyquran.R
import com.amarnehsoft.holyquran.data.GetQuranResult
import com.amarnehsoft.holyquran.databinding.FragmentDownloadQuranBinding
import com.amarnehsoft.holyquran.extensions.viewModel
import com.amarnehsoft.holyquran.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class DownloadQuranFragment : Fragment() {

    private val viewModel by lazy { viewModel<DownloadQuranViewModel>() }
    private lateinit var binding: FragmentDownloadQuranBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_download_quran,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.quranLiveData.observe(this, Observer {
            when (it) {
                GetQuranResult.Success -> {
                    startActivity(MainActivity.newIntent(context!!))
                }
                GetQuranResult.SaveToDatabase -> {

                }
                GetQuranResult.FetchingFromNetwork -> {

                }
                is GetQuranResult.Error -> {
                    Snackbar.make(view!!, it.e.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            }
        })
        return binding.root
    }
}