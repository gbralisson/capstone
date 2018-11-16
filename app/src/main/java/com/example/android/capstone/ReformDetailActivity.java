package com.example.android.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.androidlibrary.Adapter.DailyAdapter;
import com.example.android.androidlibrary.Database.AppDatabase;
import com.example.android.androidlibrary.Model.Daily;
import com.example.android.androidlibrary.Model.Material;
import com.example.android.androidlibrary.Model.Reform;
import com.example.android.androidlibrary.Model.ReformAllDailies;
import com.example.android.androidlibrary.ViewModel.DailyFactoryViewModel;
import com.example.android.androidlibrary.ViewModel.GetDailyViewModel;
import com.example.android.androidlibrary.ViewModel.GetReformViewModel;
import com.example.android.androidlibrary.ViewModel.MaterialViewModel;
import com.example.android.androidlibrary.ViewModel.ReformFactoryViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReformDetailActivity extends AppCompatActivity implements DailyAdapter.DailyAdapterOnClickListener{

    private static final String TAG = "reform_key";
    private static final String TAG_daily = "daily_key";

    @BindView(R.id.txt_days) TextView txt_days;
    @BindView(R.id.txt_spent) TextView txt_spent;
    @BindView(R.id.rv_daily_list) RecyclerView rv_daily;

    private AppDatabase database;
    private DailyAdapter dailyAdapter;
    private Reform reform;

    private List<Daily> dailies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reform_detail);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();

        if (getIntent() != null){
            if (getIntent().hasExtra(TAG)){
                reform = getIntent().getParcelableExtra(TAG);
                dailies = (List<Daily>) getIntent().getExtras().getSerializable(TAG_daily);

                database = AppDatabase.getsInstance(this);

                actionBar.setTitle(reform.getRoom());
                txt_days.setText(reform.getDays());
                txt_spent.setText(reform.getTotal_spent());

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                rv_daily.setLayoutManager(linearLayoutManager);

                dailyAdapter = new DailyAdapter(this);
                rv_daily.setAdapter(dailyAdapter);

                dailyAdapter.setDailies(dailies);

            }
        }

    }

    @Override
    public void onClick(Daily daily) {

    }

}
