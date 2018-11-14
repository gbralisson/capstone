package com.example.android.androidlibrary;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.android.androidlibrary.Adapter.MaterialAdapter;
import com.example.android.androidlibrary.Model.Material;
import com.example.android.androidlibrary.ViewModel.MaterialViewModel;

public class MaterialFragment extends Fragment implements MaterialAdapter.MaterialAdapterOnClickListerner{

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager linearLayoutManager;
    private MaterialAdapter materialAdapter;

    private OnFragmentInteractionListener mListener;

    public MaterialFragment() {
    }

    public static MaterialFragment newInstance(String param1) {
        MaterialFragment fragment = new MaterialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_material, container, false);

        recyclerView = view.findViewById(R.id.rv_materials_list);
        recyclerView.setLayoutManager(linearLayoutManager);

        materialAdapter = new MaterialAdapter(this);
        recyclerView.setAdapter(materialAdapter);
        recyclerView.setHasFixedSize(true);

        setupMaterialViewModel();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMaterialFragmentInteraction(uri);
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
    public void onClick(Material material) {

    }

    public void setupMaterialViewModel(){
        MaterialViewModel materialViewModel = ViewModelProviders.of(this).get(MaterialViewModel.class);
        materialViewModel.getMaterials().observe(this, new Observer<Material[]>() {
            @Override
            public void onChanged(@Nullable Material[] materials) {
            if (materials.length == 0)
                Log.d("teste", "No material");
            else
                materialAdapter.setMaterials(materials);
            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onMaterialFragmentInteraction(Uri uri);
    }

    public void setLinearLayoutManager(RecyclerView.LayoutManager layoutManager){
        this.linearLayoutManager = layoutManager;
    }
}
