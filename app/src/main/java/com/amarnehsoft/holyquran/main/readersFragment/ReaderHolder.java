package com.amarnehsoft.holyquran.main.readersFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.holyquran.R;

public class ReaderHolder extends RecyclerView.ViewHolder {
    ImageView img;
    TextView txtName;
    public ReaderHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img);
        txtName = itemView.findViewById(R.id.txtName);
    }
}
