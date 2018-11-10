package com.example.android.capstone;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.androidlibrary.BudgetFragment;
import com.example.android.androidlibrary.Database.AppDatabase;
import com.example.android.androidlibrary.MaterialFragment;
import com.example.android.androidlibrary.Model.Reform;
import com.example.android.androidlibrary.Model.User;
import com.example.android.androidlibrary.Utils.Utilities;
import com.example.android.androidlibrary.ViewModel.ReformViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

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

    FloatingActionButton fab_reform;
    FloatingActionButton fab_material;
    FloatingActionButton fab_budget;

    Boolean isFabOpen = false;

    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = AppDatabase.getsInstance(this);

        fab_reform = findViewById(R.id.fab_reform);
        fab_material = findViewById(R.id.fab_material);
        fab_budget = findViewById(R.id.fab_budget);

        openFab();

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
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater layoutInflater = MainActivity.this.getLayoutInflater();
                View viewDialog = layoutInflater.inflate(R.layout.dialog_reform, null);

                dialog.setView(viewDialog);
                dialog.setTitle("Reform register");

                final EditText room = viewDialog.findViewById(R.id.edt_dialog_room);
                final EditText days = viewDialog.findViewById(R.id.edt_dialog_days);
                final EditText spent = viewDialog.findViewById(R.id.edt_dialog_spent);

                dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Reform reform = new Reform();
                        reform.setRoom(room.getText().toString());
                        reform.setDays(days.getText().toString());
                        reform.setTotal_spent(spent.getText().toString());
                        insertDatabase(reform);
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
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

        if (id == R.id.nav_reforms) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            reformFragment.setLayoutManager(linearLayoutManager);

            fragmentManager.beginTransaction().replace(R.id.frame_content_main, reformFragment).commit();
        } else if (id == R.id.nav_materials) {
            fragmentManager.beginTransaction().replace(R.id.frame_content_main, MaterialFragment.newInstance("test")).commit();
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

    public void insertDatabase(Reform reform){
        new InsertReformDB().execute(reform);
    }

    public class InsertReformDB extends AsyncTask<Reform, Void, Void>{

        @Override
        protected Void doInBackground(Reform... reforms) {
            Reform reform = reforms[0];
            database.reformDAO().insertReform(reform);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(getApplicationContext(), "Reform has been added", Toast.LENGTH_SHORT).show();
        }
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
