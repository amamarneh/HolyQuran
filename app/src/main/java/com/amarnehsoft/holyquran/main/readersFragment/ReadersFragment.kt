package com.amarnehsoft.holyquran.main.readersFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.amarnehsoft.holyquran.R
import com.amarnehsoft.holyquran.test.Reader
import com.bumptech.glide.Glide

class ReadersFragment : Fragment(), ReadersAdapter.Listener {

    private var mListener: OnFragmentInteractionListener? = null

    private var recyclerView: RecyclerView? = null
    private var txtReaderName: TextView? = null
    private var readerImg: ImageView? = null
    private var adapter: ReadersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_readers, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        txtReaderName = view.findViewById(R.id.txtReaderName)
        readerImg = view.findViewById(R.id.img)

        recyclerView!!.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val decoration = DividerItemDecoration(context!!, LinearLayoutManager.HORIZONTAL)
        recyclerView!!.addItemDecoration(decoration)

        adapter = ReadersAdapter(layoutInflater, this)
        recyclerView!!.adapter = adapter
        updateView()

        return view
    }

    private fun updateView() {
        txtReaderName!!.text =
                getString(R.string.reader_name, ReaderController.currentReader.fullName)
        if (!TextUtils.isEmpty(ReaderController.currentReader.imgUrl)) {
            Glide.with(context!!).load(ReaderController.currentReader.imgUrl).into(readerImg!!)
        }
    }

    override fun onAttach(context: Context) {
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

    override fun onReaderClicked(reader: Reader) {
        ReaderController.changeReader(reader)
        updateView()
        mListener!!.resetAndPlay(reader)
    }

    interface OnFragmentInteractionListener {
        fun resetAndPlay(reader: Reader)
    }

    companion object {

        fun newInstance(): ReadersFragment {
            val fragment = ReadersFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
