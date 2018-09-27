package com.amarnehsoft.holyquran.main.readersFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amarnehsoft.holyquran.R;
import com.amarnehsoft.holyquran.test.Reader;
import com.amarnehsoft.holyquran.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReadersAdapter extends RecyclerView.Adapter<ReaderHolder> {
    private List<Reader> readers;

    private LayoutInflater inflater;
    private Listener listener;

    ReadersAdapter(LayoutInflater inflater, Listener listener){
        this.inflater = inflater;
        this.listener = listener;
        this.readers = ReadersProvider.getReaders();
    }

    @NonNull
    @Override
    public ReaderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_reader, viewGroup, false);
        return new ReaderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReaderHolder holder, int i) {
        Reader reader = readers.get(i);
        holder.txtName.setText(reader.getName());
        if (!TextUtils.isEmpty(reader.getImgUrl())){
            Picasso.get().load(reader.getImgUrl()).transform(new CircleTransform()).into(holder.img);
        }

        holder.itemView.setOnClickListener(view -> {
            if (ReaderController.getCurrentReader() != reader){
                listener.onReaderClicked(reader);
            }
        });

    }

    @Override
    public int getItemCount() {
        return readers.size();
    }

    public interface Listener{
        void onReaderClicked(Reader reader);
    }
}
