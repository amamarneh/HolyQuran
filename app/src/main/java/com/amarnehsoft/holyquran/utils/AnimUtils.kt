package com.amarnehsoft.holyquran.utils

import androidx.recyclerview.widget.RecyclerView
import android.view.animation.AnimationUtils

import com.amarnehsoft.holyquran.R

object AnimUtils {
    fun runLayoutAnimation(recyclerView: RecyclerView) {
        val context = recyclerView.context
        val controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)

        recyclerView.layoutAnimation = controller
        recyclerView.adapter!!.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()
    }
}
