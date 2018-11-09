package com.example.android.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.androidlibrary.Model.Reform;
import com.example.android.androidlibrary.ViewModel.ReformViewModel;
import com.example.android.capstone.Adapter.ReformAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReformFragment extends Fragment implements ReformAdapter.ReformAdapterOnClickHandler{

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private Context context;
    private OnFragmentInteractionListener mListener;
    private ReformAdapter reformAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;

    RecyclerView recyclerView;

    public ReformFragment() {
    }

    public static ReformFragment newInstance() {
        ReformFragment fragment = new ReformFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_reform, container, false);

        recyclerView = view.findViewById(R.id.rv_reforms_list);

        recyclerView.setLayoutManager(linearLayoutManager);

        reformAdapter = new ReformAdapter(this);

        recyclerView.setAdapter(reformAdapter);
        recyclerView.setHasFixedSize(true);

        setupViewModelReform();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onReformFragmentInteraction(uri);
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
    public void onClick(Reform reform) {

    }

    public interface OnFragmentInteractionListener {
        void onReformFragmentInteraction(Uri uri);
    }

    public void setupViewModelReform(){
        ReformViewModel reformViewModel = ViewModelProviders.of(this).get(ReformViewModel.class);
        reformViewModel.getReforms().observe(this, new Observer<Reform[]>() {
            @Override
            public void onChanged(@Nullable Reform[] reforms) {
                if (reforms.length == 0){
                    Log.d("teste", "no reforms");
                } else {
                    reformAdapter.setReforms(reforms);
                }
            }
        });
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        this.linearLayoutManager = layoutManager;
    }

}
