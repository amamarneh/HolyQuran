package com.amarnehsoft.holyquran.main.readersFragment

import androidx.recyclerview.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup

import com.amarnehsoft.holyquran.R
import com.amarnehsoft.holyquran.test.Reader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ReadersAdapter internal constructor(
    private val inflater: LayoutInflater,
    private val listener: Listener
) : RecyclerView.Adapter<ReaderHolder>() {
    private val readers: List<Reader> = ReadersProvider.readers

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ReaderHolder {
        val view = inflater.inflate(R.layout.row_reader, viewGroup, false)
        return ReaderHolder(view)
    }

    override fun onBindViewHolder(holder: ReaderHolder, i: Int) {
        val reader = readers[i]
        holder.txtName.text = reader.shortName
        if (!TextUtils.isEmpty(reader.imgUrl)) {
            Glide.with(holder.itemView.context).load(reader.imgUrl).apply(RequestOptions.circleCropTransform())
                .into(holder.img)
        }

        holder.itemView.setOnClickListener { view ->
            if (ReaderController.currentReader !== reader) {
                listener.onReaderClicked(reader)
            }
        }

    }

    override fun getItemCount(): Int {
        return readers.size
    }

    interface Listener {
        fun onReaderClicked(reader: Reader)
    }
}
