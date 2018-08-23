package com.amarnehsoft.holyquran.fragments.chapters;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amarnehsoft.holyquran.R;
import com.amarnehsoft.holyquran.Repo;
import com.amarnehsoft.holyquran.databinding.RowSuraBinding;
import com.amarnehsoft.holyquran.model.Surah;
import com.amarnehsoft.holyquran.viewmodel.ViewModelFactory;
import com.amarnehsoft.holyquran.databinding.FragmentChaptersBinding;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ChaptersFragment extends DialogFragment {
    public static final String TAG = ChaptersFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    @Inject
    Repo repo;

    @Inject
    ViewModelFactory factory;

    private ChaptersFragmentViewModel viewModel;
    private RecyclerView recyclerView;
    private Adapter adapter;

    public ChaptersFragment() {
        // Required empty public constructor
    }

    public static ChaptersFragment newInstance() {
        ChaptersFragment fragment = new ChaptersFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,factory).get(ChaptersFragmentViewModel.class);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentChaptersBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_chapters, container,false);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getResultLive().observe(this, list -> {
            adapter = new Adapter(list);
            recyclerView.setAdapter(adapter);
        });

        viewModel.getError().observe(this, s -> Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
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

    class Adapter extends RecyclerView.Adapter<Holder>{
        private List<Surah> list;

        Adapter(List<Surah> list){
            this.list= list;
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            RowSuraBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.row_sura, viewGroup, false);
            Holder holder = new Holder(binding);
            binding.getRoot().setOnClickListener(view -> {
                mListener.chapterSelected(list.get(holder.getAdapterPosition()));
                dismiss();
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int i) {
            holder.binding.setBean(list.get(holder.getAdapterPosition()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class Holder extends RecyclerView.ViewHolder{
        RowSuraBinding binding;
        Holder(@NonNull RowSuraBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnFragmentInteractionListener {
        void chapterSelected(Surah surah);
    }
}
