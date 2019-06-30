package com.amarnehsoft.holyquran.main.readersFragment

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.amarnehsoft.holyquran.R

class ReaderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var img: ImageView = itemView.findViewById(R.id.img)
    var txtName: TextView = itemView.findViewById(R.id.txtName)
}
