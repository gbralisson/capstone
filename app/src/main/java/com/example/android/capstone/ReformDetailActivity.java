package com.example.android.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.androidlibrary.Adapter.DailyAdapter;
import com.example.android.androidlibrary.Database.DailyDatabase;
import com.example.android.androidlibrary.Model.Daily;
import com.example.android.androidlibrary.Model.Material;
import com.example.android.androidlibrary.Model.Reform;
import com.example.android.androidlibrary.ViewModel.DailyFactoryViewModel;
import com.example.android.androidlibrary.ViewModel.GetDailyViewModel;
import com.example.android.androidlibrary.ViewModel.MaterialViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReformDetailActivity extends AppCompatActivity implements DailyAdapter.DailyAdapterOnClickListener{

    private static final String TAG = "reform_key";
    @BindView(R.id.txt_days) TextView txt_days;
    @BindView(R.id.txt_spent) TextView txt_spent;
    @BindView(R.id.fab_daily) FloatingActionButton fab_daily;
    @BindView(R.id.rv_daily_list) RecyclerView rv_daily;

    private DailyDatabase dailyDatabase;
    private DailyAdapter dailyAdapter;

    private Material[] materials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reform_detail);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();

        if (getIntent() != null){
            if (getIntent().hasExtra(TAG)){
                Reform reform = getIntent().getParcelableExtra(TAG);

                dailyDatabase = DailyDatabase.getsInstance(this);

                actionBar.setTitle(reform.getRoom());
                txt_days.setText(reform.getDays());
                txt_spent.setText(reform.getTotal_spent());

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                rv_daily.setLayoutManager(linearLayoutManager);

                dailyAdapter = new DailyAdapter(this);
                rv_daily.setAdapter(dailyAdapter);

                setupViewModel(reform);
                setupMaterialViewModel();

            }
        }

        fab_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("teste", materials[0].getMaterial());
            }
        });

    }

    @Override
    public void onClick(Daily daily) {

    }

    public void setupViewModel(Reform reform){
        DailyFactoryViewModel dailyFactoryViewModel = new DailyFactoryViewModel(dailyDatabase, reform.getId());
        GetDailyViewModel getDailyViewModel = ViewModelProviders.of(this, dailyFactoryViewModel).get(GetDailyViewModel.class);

        getDailyViewModel.getDailies().observe(this, new Observer<Daily[]>() {
            @Override
            public void onChanged(@Nullable Daily[] dailies) {
                if (dailies.length != 0) {
                    dailyAdapter.setDailies(dailies);
                }
                else
                    Log.d("teste", "no daily");
            }
        });
    }

    public void setupMaterialViewModel(){
        MaterialViewModel materialViewModel = ViewModelProviders.of(this).get(MaterialViewModel.class);
        materialViewModel.getMaterials().observe(this, new Observer<Material[]>() {
            @Override
            public void onChanged(@Nullable Material[] materials) {
                if (materials.length == 0)
                    Log.d("teste", "No material");
                else
                    setMaterials(materials);
            }
        });
    }

    public void setMaterials(Material[] materials){
        this.materials = materials;
    }
}
