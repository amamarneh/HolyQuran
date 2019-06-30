package com.amarnehsoft.holyquran.fragments.verses

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
import com.amarnehsoft.holyquran.databinding.FragmentVersesBinding
import com.amarnehsoft.holyquran.databinding.RowVerseBinding
import com.amarnehsoft.holyquran.viewmodel.ViewModelFactory

import java.util.ArrayList

import javax.inject.Inject

import dagger.android.support.AndroidSupportInjection

class VersesFragment : DialogFragment() {
    private var mListener: OnFragmentInteractionListener? = null

    @Inject
    lateinit var factory: ViewModelFactory

    private var viewModel: VersesFragmentViewModel? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, factory).get(VersesFragmentViewModel::class.java)
        if (arguments != null) {
            viewModel!!.numberOfVerses = arguments!!.getInt("verses")
            viewModel!!.numberOfStartAya = arguments!!.getInt("num")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentVersesBinding>(
            inflater,
            R.layout.fragment_verses,
            container,
            false
        )
        binding.setLifecycleOwner(this)
        binding.viewmodel = viewModel
        viewModel!!.init()
        val view = binding.root
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView!!.layoutManager = LinearLayoutManager(context)

        viewModel!!.resultLive.observe(this, Observer{ list ->
            if (list != null) {
                adapter = Adapter(list)
                recyclerView!!.adapter = adapter
            }
        })

        viewModel!!.error.observe(
            this,
            Observer{ s -> Toast.makeText(context, s, Toast.LENGTH_LONG).show() })
        return view
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    internal inner class Adapter(list: List<Int>) : RecyclerView.Adapter<Holder>() {
        private val list: MutableList<Item>

        init {
            this.list = ArrayList()
            for (i in list) {
                this.list.add(Item(i))
            }
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): Holder {
            val binding = DataBindingUtil.inflate<RowVerseBinding>(
                layoutInflater,
                R.layout.row_verse,
                viewGroup,
                false
            )
            val holder = Holder(binding)
            binding.root.setOnClickListener { view ->
                mListener!!.ayaSelected(viewModel!!.numberOfStartAya - 1 + list[holder.adapterPosition].ayaNumber)
                dismiss()
            }
            return holder
        }

        override fun onBindViewHolder(holder: Holder, i: Int) {
            holder.binding.bean = list[holder.adapterPosition]
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    internal inner class Holder(var binding: RowVerseBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class Item internal constructor(var ayaNumber: Int)

    interface OnFragmentInteractionListener {
        fun ayaSelected(ayaNumber: Int)
    }

    companion object {
        val TAG = VersesFragment::class.java.simpleName

        fun newInstance(numberOfStartAya: Int, numberOfVerses: Int): VersesFragment {
            val fragment = VersesFragment()
            val args = Bundle()
            args.putInt("verses", numberOfVerses)
            args.putInt("num", numberOfStartAya)
            fragment.arguments = args
            return fragment
        }
    }
}
