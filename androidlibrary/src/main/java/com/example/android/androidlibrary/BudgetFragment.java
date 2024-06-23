package com.example.android.androidlibrary;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.androidlibrary.Database.AppDatabase;
import com.example.android.androidlibrary.Model.Material;
import com.example.android.androidlibrary.Model.Reform;
import com.example.android.androidlibrary.Model.ReformAllDailies;
import com.example.android.androidlibrary.ViewModel.GetReformViewModel;
import com.example.android.androidlibrary.ViewModel.ReformFactoryViewModel;
import com.example.android.androidlibrary.ViewModel.ReformViewModel;

import java.io.Serializable;
import java.util.List;

public class BudgetFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    private TextView txt_budget;
    private TextView txt_spent;
    private TextView txt_remain;

    private OnFragmentInteractionListener mListener;

    public BudgetFragment() {
    }

    public static BudgetFragment newInstance() {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_budget, container, false);
        txt_budget = view.findViewById(R.id.txt_budget);
        txt_spent = view.findViewById(R.id.txt_spent);
        txt_remain = view.findViewById(R.id.txt_remaining);

        setupDailiesViewModel();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onBudgetFragmentInteraction(uri);
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

    public interface OnFragmentInteractionListener {
        void onBudgetFragmentInteraction(Uri uri);
    }

    public void setupDailiesViewModel(){
        ReformViewModel reformViewModel = ViewModelProviders.of(this).get(ReformViewModel.class);
        reformViewModel.getReformAllDailiesLiveData().observe(getActivity(), new Observer<List<ReformAllDailies>>() {
            @Override
            public void onChanged(@Nullable List<ReformAllDailies> reformAllDailies) {

                double spent = getSpentFromReform(reformAllDailies);
                double budget = getBudgetFromReform(reformAllDailies);
                double remain = budget - spent;

                txt_budget.setText(String.valueOf(budget));
                txt_spent.setText(String.valueOf(spent));
                txt_remain.setText(String.valueOf(remain));

            }
        });

    }

    public Double getSpentFromReform(List<ReformAllDailies> reformAllDailies){
        double value = 0.0;
        for (int i=0; i<reformAllDailies.size(); i++){
            for (int j=0; j<reformAllDailies.get(i).getDailies().size(); j++){
                double spent = Double.parseDouble(reformAllDailies.get(i).getDailies().get(j).getMaterial().getValue());
                int quantity = reformAllDailies.get(i).getDailies().get(j).getQuantity();
                value += (spent*quantity);
            }
        }
        return value;
    }

    public Double getBudgetFromReform(List<ReformAllDailies> reformAllDailies){
        double value = 0.0;
        for (int i=0; i<reformAllDailies.size(); i++){
            double budget = Double.parseDouble(reformAllDailies.get(i).getReform().getTotal_spent());
            value += budget;
        }
        return value;
    }

}
