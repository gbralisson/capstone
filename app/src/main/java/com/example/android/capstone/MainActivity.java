package com.example.android.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.androidlibrary.BudgetFragment;
import com.example.android.androidlibrary.Database.AppDatabase;
import com.example.android.androidlibrary.MaterialFragment;
import com.example.android.androidlibrary.Model.Daily;
import com.example.android.androidlibrary.Model.Material;
import com.example.android.androidlibrary.Model.Reform;
import com.example.android.androidlibrary.Model.ReformAllDailies;
import com.example.android.androidlibrary.Utils.Utilities;
import com.example.android.androidlibrary.ViewModel.GetReformViewModel;
import com.example.android.androidlibrary.ViewModel.MaterialViewModel;
import com.example.android.androidlibrary.ViewModel.ReformFactoryViewModel;
import com.example.android.androidlibrary.ViewModel.ReformViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ReformFragment.OnFragmentInteractionListener,
        MaterialFragment.OnFragmentInteractionListener, BudgetFragment.OnFragmentInteractionListener{

    private DrawerLayout drawer;
    private FragmentManager fragmentManager;
    private ReformFragment reformFragment;
    private static final String USER_KEY = "userkey";
    private static final String USER_SIGN_CLIENT = "userSignInClient";

    @BindView(R.id.txt_nav_title) TextView nav_title;
    @BindView(R.id.txt_nav_subtitle) TextView nav_subtitle;
    @BindView(R.id.img_profile_google) ImageView img_profile;

    private FloatingActionButton fab_reform;
    private FloatingActionButton fab_material;
    private FloatingActionButton fab_budget;

    private LinearLayoutManager linearLayoutManager;

    Boolean isFabOpen = false;

    AppDatabase appDatabase;
    private List<String> units = new ArrayList<>();
    private Material[] materials;
    private Reform[] reforms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.txt_AGDaily));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appDatabase = AppDatabase.getsInstance(this);

        fab_reform = findViewById(R.id.fab_reform);
        fab_material = findViewById(R.id.fab_material);
        fab_budget = findViewById(R.id.fab_budget);

        openFab();
        fillSpinner();
        setupReformViewModel();
        setupMaterialViewModel();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFabOpen){
                    closeFab();
                } else {
                    openFab();
                }
            }
        });

        fab_reform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                View viewDialog = layoutInflater.inflate(R.layout.dialog_reform, null);

                dialog.setView(viewDialog);
                dialog.setTitle(getString(R.string.reform_register));

                final EditText room = viewDialog.findViewById(R.id.edt_dialog_room);
                final EditText days = viewDialog.findViewById(R.id.edt_dialog_days);
                final EditText spent = viewDialog.findViewById(R.id.edt_dialog_spent);

                dialog.setPositiveButton(getString(R.string.btn_register), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Reform reform = new Reform();
                        reform.setRoom(room.getText().toString());
                        reform.setDays(days.getText().toString());
                        reform.setTotal_spent(spent.getText().toString());
                        insertReformDatabase(reform);
                    }
                });

                dialog.setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                }).create();
                dialog.show();
            }
        });

        fab_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                View viewDialog = layoutInflater.inflate(R.layout.dialog_material, null);

                dialog.setView(viewDialog);
                dialog.setTitle("Material register");

                final EditText edt_material = viewDialog.findViewById(R.id.edt_material);
                final EditText edt_value = viewDialog.findViewById(R.id.edt_value);
                final Spinner spn_unit = viewDialog.findViewById(R.id.spn_unit);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, units);
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                spn_unit.setAdapter(adapter);

                dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final Material material = new Material();
                        material.setMaterial(edt_material.getText().toString());
                        material.setValue(edt_value.getText().toString());
                        material.setUnit(spn_unit.getSelectedItem().toString());

                        insertMaterialDatabase(material);
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                }).create();
                dialog.show();
            }
        });

        fab_budget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                View viewDialog = layoutInflater.inflate(R.layout.dialog_daily, null);

                dialog.setView(viewDialog);
                dialog.setTitle(getString(R.string.daily_register));

                final Spinner spnMaterial = viewDialog.findViewById(R.id.spn_material);
                final EditText edtQuantity = viewDialog.findViewById(R.id.edt_quantity);
                final Spinner spnReform = viewDialog.findViewById(R.id.spn_reform);

                ArrayList<Material> listMaterial = setSpinnerListMaterial(materials);
                ArrayList<Reform> listReforms = setSpinnerListReforms(reforms);

                final ArrayAdapter<Material> adapterMaterial = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listMaterial);
                adapterMaterial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnMaterial.setAdapter(adapterMaterial);

                final ArrayAdapter<Reform> adapterReform = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, listReforms);
                adapterReform.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnReform.setAdapter(adapterReform);

                dialog.setPositiveButton(getString(R.string.btn_register), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Material material = (Material) spnMaterial.getSelectedItem();
                        Reform reform = (Reform) spnReform.getSelectedItem();
                        Daily daily = getDaily(material, reform, Integer.parseInt(edtQuantity.getText().toString()));
                        insertDailyDatabase(daily);
                    }
                });

                dialog.setNegativeButton(getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create();
                dialog.show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        fragmentManager = getSupportFragmentManager();
        reformFragment = ReformFragment.newInstance();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reformFragment.setLayoutManager(linearLayoutManager);

        fragmentManager.beginTransaction().replace(R.id.frame_content_main, reformFragment).commit();

        if (getIntent() != null){
            if (getIntent().hasExtra(USER_KEY)){
                GoogleSignInAccount account = getIntent().getParcelableExtra(USER_KEY);

                View nav_header = navigationView.getHeaderView(0);
                ButterKnife.bind(this, nav_header);

                nav_title.setText(account.getGivenName());
                nav_subtitle.setText(account.getEmail());
                Utilities.loadImageProfile(this, String.valueOf(account.getPhotoUrl()), img_profile);

            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        if (id == R.id.nav_reforms) {
            reformFragment.setLayoutManager(linearLayoutManager);
            fragmentManager.beginTransaction().replace(R.id.frame_content_main, reformFragment).commit();
        } else if (id == R.id.nav_materials) {
            MaterialFragment materialFragment = MaterialFragment.newInstance("");
            materialFragment.setLinearLayoutManager(linearLayoutManager);
            fragmentManager.beginTransaction().replace(R.id.frame_content_main, materialFragment).commit();
        } else if (id == R.id.nav_budget) {
            fragmentManager.beginTransaction().replace(R.id.frame_content_main, BudgetFragment.newInstance("test")).commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {
            signOut();
        } else if (id == R.id.nav_exit) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onReformFragmentInteraction(Uri uri) {
    }

    @Override
    public void onMaterialFragmentInteraction(Uri uri) {
    }

    @Override
    public void onBudgetFragmentInteraction(Uri uri) {
    }

    private void signOut(){
        LoginActivity.getGoogleSignInClient().signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
            }
        });
    }

    private void closeFab(){
        isFabOpen = false;
        fab_reform.animate().translationY(0);
        fab_material.animate().translationY(0);
        fab_budget.animate().translationY(0);
    }

    private void openFab(){
        isFabOpen = true;
        fab_reform.animate().translationY(getResources().getDimension(R.dimen.fab_margin_reform));
        fab_material.animate().translationY(getResources().getDimension(R.dimen.fab_margin_material));
        fab_budget.animate().translationY(getResources().getDimension(R.dimen.fab_margin_budget));
    }

    public void insertReformDatabase(Reform reform){
        new InsertReformDB().execute(reform);
    }

    public class InsertReformDB extends AsyncTask<Reform, Void, Void>{

        @Override
        protected Void doInBackground(Reform... reforms) {
            Reform reform = reforms[0];
            appDatabase.reformDAO().insertReform(reform);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Reform has been added", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertMaterialDatabase(Material material){
        new InsertMaterialDB().execute(material);
    }

    public class InsertMaterialDB extends AsyncTask<Material, Void, Void>{

        @Override
        protected Void doInBackground(Material... materials) {
            Material material = materials[0];
            appDatabase.materialDAO().insertMaterial(material);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Material has been added", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertDailyDatabase(Daily daily){
        new InsertDailyDB().execute(daily);
    }

    public class InsertDailyDB extends AsyncTask<Daily, Void, Void>{

        @Override
        protected Void doInBackground(Daily... dailies) {
            Daily daily = dailies[0];
            appDatabase.dailyDAO().insertDaily(daily);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Daily has been added", Toast.LENGTH_SHORT).show();
        }
    }

    public void fillSpinner(){
        units.add("Kg");
        units.add("g");
        units.add("mg");
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

    public void setupReformViewModel(){
        ReformViewModel reformViewModel = ViewModelProviders.of(this).get(ReformViewModel.class);
        reformViewModel.getReforms().observe(this, new Observer<Reform[]>() {
            @Override
            public void onChanged(@Nullable Reform[] reforms) {
                if (reforms.length == 0){
                    Log.d("teste", "No reform");
                } else {
                    setReforms(reforms);
                }
            }
        });
    }

    public ArrayList<Material> setSpinnerListMaterial(Material[] materials){
        ArrayList<Material> listMaterial = new ArrayList<>();
        Collections.addAll(listMaterial, materials);
        return listMaterial;
    }

    public ArrayList<Reform> setSpinnerListReforms(Reform[] reforms){
        ArrayList<Reform> listReforms = new ArrayList<>();
        Collections.addAll(listReforms, reforms);
        return listReforms;
    }

    public void setMaterials(Material[] materials){
        this.materials = materials;
    }

    public void setReforms(Reform[] reforms){
        this.reforms = reforms;
    }

    public Daily getDaily(Material material, Reform reform, int quantity){
        Daily daily = new Daily();
        daily.setId_reform(reform.getId());
        daily.setMaterial(material);
        daily.setQuantity(quantity);
        return daily;
    }
//
//    private void revokeAccess(){
//        googleSignInClient.revokeAccess().addOnCompleteListener(this, new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                updateUIGoogle(null);
//            }
//        });
//    }

}
