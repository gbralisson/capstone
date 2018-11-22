package com.example.android.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.example.android.androidlibrary.Database.AppDatabase;
import com.example.android.androidlibrary.Model.Daily;
import com.example.android.androidlibrary.Model.Material;
import com.example.android.androidlibrary.Model.Reform;
import com.example.android.androidlibrary.Model.ReformAllDailies;
import com.example.android.androidlibrary.ViewModel.GetReformViewModel;
import com.example.android.androidlibrary.ViewModel.MaterialViewModel;
import com.example.android.androidlibrary.ViewModel.ReformFactoryViewModel;
import com.example.android.androidlibrary.ViewModel.ReformViewModel;
import com.example.android.androidlibrary.Widget.ReformWidget;
import com.example.android.capstone.Adapter.ReformAdapter;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReformFragment extends Fragment implements ReformAdapter.ReformAdapterOnClickHandler{

    private static final String TAG = "reform_key";
    private static final String TAG_daily = "daily_key";

    private OnFragmentInteractionListener mListener;
    private ReformAdapter reformAdapter;
    private RecyclerView.LayoutManager linearLayoutManager;

    private RecyclerView recyclerView;
    private TextView txt_noReform;

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
        txt_noReform = view.findViewById(R.id.txt_noReform);
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
        setupDailiesViewModel(reform);
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
                    txt_noReform.setVisibility(View.VISIBLE);
                } else {
                    txt_noReform.setVisibility(View.GONE);
                    reformAdapter.setReforms(reforms);
                    (new ReformWidget()).getReform(getContext(), reforms);
                }
            }
        });

    }

    public void setupDailiesViewModel(final Reform reform){
        ReformFactoryViewModel reformFactoryViewModel = new ReformFactoryViewModel(AppDatabase.getsInstance(getActivity()), reform.getId());
        GetReformViewModel getReformViewModel = ViewModelProviders.of(this, reformFactoryViewModel).get(GetReformViewModel.class);

        getReformViewModel.getReformAllDailiesLiveData().observe(this, new Observer<ReformAllDailies>() {
            @Override
            public void onChanged(@Nullable ReformAllDailies reformAllDailies) {
                if (reformAllDailies != null){
                    Intent intent = new Intent(getActivity(), ReformDetailActivity.class);
                    intent.putExtra(TAG, reform);
                    intent.putExtra(TAG_daily, (Serializable) reformAllDailies.getDailies());
                    startActivity(intent);

                }
            }
        });
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        this.linearLayoutManager = layoutManager;
    }

}
