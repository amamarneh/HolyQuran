package com.amarnehsoft.holyquran.main.readersFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amarnehsoft.holyquran.R;
import com.amarnehsoft.holyquran.test.Reader;
import com.amarnehsoft.holyquran.utils.AnimUtils;
import com.squareup.picasso.Picasso;

public class ReadersFragment extends Fragment implements ReadersAdapter.Listener {

    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private TextView txtReaderName;
    private ImageView readerImg;
    private ReadersAdapter adapter;

    public ReadersFragment() {
        // Required empty public constructor
    }

    public static ReadersFragment newInstance() {
        ReadersFragment fragment = new ReadersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) { }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  =inflater.inflate(R.layout.fragment_readers, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        txtReaderName = view.findViewById(R.id.txtReaderName);
        readerImg = view.findViewById(R.id.img);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), LinearLayoutManager.HORIZONTAL);
        recyclerView.addItemDecoration(decoration);

        adapter = new ReadersAdapter(getLayoutInflater(), this);
        recyclerView.setAdapter(adapter);
        updateView();

        return view;
    }

    private void updateView(){
        txtReaderName.setText(getString(R.string.reader_name,   ReaderController.getCurrentReader().getFullName()));
        if (!TextUtils.isEmpty(ReaderController.getCurrentReader().getImgUrl())) {
            Picasso.with(getContext()).load(ReaderController.getCurrentReader().getImgUrl()).into(readerImg);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onReaderClicked(Reader reader) {
        ReaderController.changeReader(reader);
        updateView();
        mListener.resetAndPlay(reader);
    }

    public interface OnFragmentInteractionListener {
        void resetAndPlay(Reader reader);
    }
}
