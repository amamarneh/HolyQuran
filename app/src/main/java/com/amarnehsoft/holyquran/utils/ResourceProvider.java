package com.amarnehsoft.holyquran.utils;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ResourceProvider {

    private Context mContext;

    @Inject
    public ResourceProvider(Context mContext) {
        this.mContext = mContext;
    }

    public String getString(int resId) {
        return mContext.getString(resId);
    }

    public String getString(int resId, String value) {
        return mContext.getString(resId, value);
    }
}