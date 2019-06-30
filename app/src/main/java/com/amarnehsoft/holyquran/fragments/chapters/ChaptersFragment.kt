package com.amarnehsoft.holyquran.fragments.chapters

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.amarnehsoft.holyquran.R
import com.amarnehsoft.holyquran.databinding.RowSuraBinding
import com.amarnehsoft.holyquran.viewmodel.ViewModelFactory
import com.amarnehsoft.holyquran.databinding.FragmentChaptersBinding
import com.amarnehsoft.holyquran.model.Result
import com.amarnehsoft.holyquran.model.Surah
import com.amarnehsoft.holyquran.network.quran.Quran

import javax.inject.Inject

import dagger.android.support.AndroidSupportInjection

class ChaptersFragment : DialogFragment() {
    private var mListener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var factory: ViewModelFactory

    private var viewModel: ChaptersFragmentViewModel? = null
    private var adapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(ChaptersFragmentViewModel::class.java)
        if (arguments != null) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentChaptersBinding>(
            inflater,
            R.layout.fragment_chapters,
            container,
            false
        )
        binding.setLifecycleOwner(this)
        binding.viewmodel = viewModel
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel!!.resultLive.observe(this, Observer { result ->
            adapter = Adapter((result as Result.Success).data)
            binding.recyclerView.adapter = adapter
        })

        viewModel!!.error.observe(
            this,
            Observer{ s -> Toast.makeText(context, s, Toast.LENGTH_LONG).show() })
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private inner class Adapter(private val list: List<Surah>) : RecyclerView.Adapter<Holder>() {

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
            val binding = DataBindingUtil.inflate<RowSuraBinding>(
                layoutInflater,
                R.layout.row_sura,
                viewGroup,
                false
            )
            val holder = Holder(binding)
            binding.root.setOnClickListener { view ->
                mListener!!.chapterSelected(list[holder.adapterPosition])
                dismiss()
            }
            return holder
        }

        override fun onBindViewHolder(holder: Holder, i: Int) {
            holder.binding.bean = list[holder.adapterPosition].let {
                Quran.Surah(
                    it.number,
                    it.englishName,
                    it.englishNameTranslation,
                    it.name,
                    it.revelationType,
                    emptyList()
                )
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    inner class Holder(var binding: RowSuraBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnFragmentInteractionListener {
        fun chapterSelected(surah: Surah)
    }

    companion object {
        val TAG: String = ChaptersFragment::class.java.simpleName
        fun newInstance() = ChaptersFragment()
    }
}
