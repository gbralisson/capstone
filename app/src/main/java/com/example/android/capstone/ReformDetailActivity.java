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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReformDetailActivity extends AppCompatActivity implements DailyAdapter.DailyAdapterOnClickListener{

    private static final String TAG = "reform_key";
    @BindView(R.id.txt_days) TextView txt_days;
    @BindView(R.id.txt_spent) TextView txt_spent;
    @BindView(R.id.fab_daily) FloatingActionButton fab_daily;
    @BindView(R.id.rv_daily_list) RecyclerView rv_daily;

    private AppDatabase database;
    private DailyAdapter dailyAdapter;
    private Reform reform;

    private Material[] materials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reform_detail);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();

        if (getIntent() != null){
            if (getIntent().hasExtra(TAG)){
                reform = getIntent().getParcelableExtra(TAG);
                database = AppDatabase.getsInstance(this);

                actionBar.setTitle(reform.getRoom());
                txt_days.setText(reform.getDays());
                txt_spent.setText(reform.getTotal_spent());

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                rv_daily.setLayoutManager(linearLayoutManager);

                dailyAdapter = new DailyAdapter(this);
                rv_daily.setAdapter(dailyAdapter);

//                setupViewModel();
                setupDailiesViewModel();
//                setupMaterialViewModel();

            }
        }

//        fab_daily.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final AlertDialog.Builder dialog = new AlertDialog.Builder(ReformDetailActivity.this);
//
//                LayoutInflater layoutInflater = ReformDetailActivity.this.getLayoutInflater();
//                View viewDialog = layoutInflater.inflate(R.layout.dialog_daily, null);
//
//                dialog.setView(viewDialog);
//                dialog.setTitle(getString(R.string.daily_register));
//
//                final Spinner spnMaterial = viewDialog.findViewById(R.id.spn_material);
//                final EditText edtQuantity = viewDialog.findViewById(R.id.edt_quantity);
//
//                ArrayList<Material> listMaterial = setSpinnerList(materials);
//
//                final ArrayAdapter<Material> adapter = new ArrayAdapter<>(ReformDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, listMaterial);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spnMaterial.setAdapter(adapter);
//
//                dialog.setPositiveButton(getString(R.string.btn_register), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Material material = (Material) spnMaterial.getSelectedItem();
//                        Daily daily = getDaily(material);
//                        insertDailyDatabase(daily);
//                    }
//                });
//
//                dialog.setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        onBackPressed();
//                    }
//                }).create();
//                dialog.show();
//            }
//        });

    }

    @Override
    public void onClick(Daily daily) {

    }

    public void setupViewModel(){
        DailyFactoryViewModel dailyFactoryViewModel = new DailyFactoryViewModel(database, reform.getId());
        GetDailyViewModel getDailyViewModel = ViewModelProviders.of(this, dailyFactoryViewModel).get(GetDailyViewModel.class);

        getDailyViewModel.getDailies().observe(this, new Observer<Daily[]>() {
            @Override
            public void onChanged(@Nullable Daily[] dailies) {
                if (dailies.length != 0) {
//                    dailyAdapter.setDailies(dailies);
                    Log.d("teste", String.valueOf(dailies.length));
                }
                else
                    Log.d("teste", "no daily");
            }
        });
    }

//    public void setupMaterialViewModel(){
//        MaterialViewModel materialViewModel = ViewModelProviders.of(this).get(MaterialViewModel.class);
//        materialViewModel.getMaterials().observe(this, new Observer<Material[]>() {
//            @Override
//            public void onChanged(@Nullable Material[] materials) {
//                if (materials.length == 0)
//                    Log.d("teste", "No material");
//                else
//                    setMaterials(materials);
//            }
//        });
//    }

    public void setupDailiesViewModel(){
        ReformFactoryViewModel reformFactoryViewModel = new ReformFactoryViewModel(AppDatabase.getsInstance(this), reform.getId());
        GetReformViewModel getReformViewModel = ViewModelProviders.of(this, reformFactoryViewModel).get(GetReformViewModel.class);

        getReformViewModel.getReformAllDailiesLiveData().observe(this, new Observer<ReformAllDailies>() {
            @Override
            public void onChanged(@Nullable ReformAllDailies reformAllDailies) {
                if (reformAllDailies != null){
                    Log.d("teste", "Room: " + reformAllDailies.getReform().getRoom());
                    Log.d("teste", "Size: " + String.valueOf(reformAllDailies.getDailies().size()));
//                    dailyAdapter.setDailies(reformAllDailies.getDailies());
                } else{
                    Log.d("teste", "No daily");
                }
            }
        });
    }

//    public void setMaterials(Material[] materials){
//        this.materials = materials;
//    }
//
//    public ArrayList<Material> setSpinnerList(Material[] materials){
//        ArrayList<Material> listMaterial = new ArrayList<>();
//        Collections.addAll(listMaterial, materials);
//        return listMaterial;
//    }
//
//    public Daily getDaily(Material material){
//        Daily daily = new Daily();
//        daily.setId_reform(reform.getId());
//        daily.setMaterial(material);
//        return daily;
//    }

//    public void insertDailyDatabase(Daily daily){
//        new InsertDailyDB().execute(daily);
//    }
//
//    public class InsertDailyDB extends AsyncTask<Daily, Void, Void>{
//
//        @Override
//        protected Void doInBackground(Daily... dailies) {
//            Daily daily = dailies[0];
//            database.dailyDAO().insertDaily(daily);
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            Toast.makeText(ReformDetailActivity.this, "Daily has been added", Toast.LENGTH_SHORT).show();
//        }
//    }

}
