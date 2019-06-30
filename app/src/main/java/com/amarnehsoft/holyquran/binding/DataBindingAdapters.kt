package com.amarnehsoft.holyquran.binding


import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.BackgroundColorSpan
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import androidx.databinding.BindingAdapter

object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun visibleGone(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUri(view: ImageView, imageUri: String?) {
        if (imageUri == null) {
            view.setImageURI(null)
        } else {
            view.setImageURI(Uri.parse(imageUri))
        }
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUri(view: ImageView, imageUri: Uri) {
        view.setImageURI(imageUri)
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageDrawable(view: ImageView, drawable: Drawable) {
        view.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("android:enabled")
    fun setImageEnabled(imageView: ImageButton, enabled: Boolean) {
        imageView.isEnabled = enabled
    }

    @JvmStatic
    @BindingAdapter("android:txt")
    fun setTextViewText(textView: TextView, text: String?) {
        if (text == null) return
        val spanna = SpannableString(text)
        spanna.setSpan(
            BackgroundColorSpan(-0x49000000),
            0,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        textView.text = spanna
    }
}
