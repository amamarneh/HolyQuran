package com.amarnehsoft.holyquran.utils

import android.content.Context

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(private val mContext: Context) {

    fun getString(resId: Int): String {
        return mContext.getString(resId)
    }

    fun getString(resId: Int, value: String): String {
        return mContext.getString(resId, value)
    }
}