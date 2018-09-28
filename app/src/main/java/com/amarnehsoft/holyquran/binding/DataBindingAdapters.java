package com.amarnehsoft.holyquran.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DataBindingAdapters {

    @BindingAdapter("android:src")
    public static void setImageUri(ImageView view, String imageUri) {
        if (imageUri == null) {
            view.setImageURI(null);
        } else {
            view.setImageURI(Uri.parse(imageUri));
        }
    }

    @BindingAdapter("android:src")
    public static void setImageUri(ImageView view, Uri imageUri) {
        view.setImageURI(imageUri);
    }

    @BindingAdapter("android:src")
    public static void setImageDrawable(ImageView view, Drawable drawable) {
        view.setImageDrawable(drawable);
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource){
        imageView.setImageResource(resource);
    }

    @BindingAdapter("android:enabled")
    public static void setImageEnabled(ImageButton imageView, boolean enabled){
        imageView.setEnabled(enabled);
    }

    @BindingAdapter("android:txt")
    public static void setTextViewText(TextView textView, String text){
        if (text == null) return;
        String myString = text;
        Spannable spanna = new SpannableString(myString);
        spanna.setSpan(new BackgroundColorSpan(0xB7000000),0, myString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spanna);
    }
}