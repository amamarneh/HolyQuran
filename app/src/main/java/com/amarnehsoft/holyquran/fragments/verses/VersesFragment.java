package com.amarnehsoft.holyquran.fragments.verses;

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
import com.amarnehsoft.holyquran.databinding.FragmentVersesBinding;
import com.amarnehsoft.holyquran.databinding.RowVerseBinding;
import com.amarnehsoft.holyquran.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class VersesFragment extends DialogFragment {
    public static final String TAG = VersesFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;

    @Inject
    ViewModelFactory factory;

    private VersesFragmentViewModel viewModel;
    private RecyclerView recyclerView;
    private Adapter adapter;

    public VersesFragment() {
        // Required empty public constructor
    }

    public static VersesFragment newInstance(int numberOfStartAya, int numberOfVerses) {
        VersesFragment fragment = new VersesFragment();
        Bundle args = new Bundle();
        args.putInt("verses", numberOfVerses);
        args.putInt("num", numberOfStartAya);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this,factory).get(VersesFragmentViewModel.class);
        if (getArguments() != null) {
            viewModel.setNumberOfVerses(getArguments().getInt("verses"));
            viewModel.setNumberOfStartAya(getArguments().getInt("num"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
        FragmentVersesBinding binding = DataBindingUtil.inflate(inflater,R.layout.fragment_verses, container,false);
        binding.setLifecycleOwner(this);
        binding.setViewmodel(viewModel);
        viewModel.init();
        View view = binding.getRoot();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getResultLive().observe(this, list -> {
            if (list != null){
                adapter = new Adapter(list);
                recyclerView.setAdapter(adapter);
            }
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
        private List<Item> list;

        Adapter(List<Integer> list){
            this.list = new ArrayList<>();
            for (Integer i : list){
                this.list.add(new Item(i));
            }
        }

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            RowVerseBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.row_verse, viewGroup, false);
            Holder holder = new Holder(binding);
            binding.getRoot().setOnClickListener(view -> {
                mListener.ayaSelected(viewModel.getNumberOfStartAya() - 1 + list.get(holder.getAdapterPosition()).getAyaNumber());
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
        RowVerseBinding binding;
        Holder(@NonNull RowVerseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public class Item {
        Item(int ayaNumber){
            this.ayaNumber = ayaNumber;
        }
        private int ayaNumber;

        public int getAyaNumber() {
            return ayaNumber;
        }

        public void setAyaNumber(int ayaNumber) {
            this.ayaNumber = ayaNumber;
        }
    }

    public interface OnFragmentInteractionListener {
        void ayaSelected(int ayaNumber);
    }
}
