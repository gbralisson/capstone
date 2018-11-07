package com.example.android.capstone;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.androidlibrary.BudgetFragment;
import com.example.android.androidlibrary.MaterialFragment;
import com.example.android.capstone.Model.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ReformFragment.OnFragmentInteractionListener,
        MaterialFragment.OnFragmentInteractionListener, BudgetFragment.OnFragmentInteractionListener{

    DrawerLayout drawer;
    FragmentManager fragmentManager;
    private static final String USER_KEY = "userkey";
    private User user;

    @BindView(R.id.txt_nav_title) TextView nav_title;
    @BindView(R.id.txt_nav_subtitle) TextView nav_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        fragmentManager.beginTransaction().replace(R.id.frame_content_main, ReformFragment.newInstance("test")).commit();

        if (getIntent() != null){
            if (getIntent().hasExtra(USER_KEY)){
                user = getIntent().getParcelableExtra(USER_KEY);
                Log.d("teste", user.getUsername());
                Log.d("teste", user.getEmail());

//                nav_title.setText(user.getUsername());
//                nav_subtitle.setText(user.getEmail());
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reforms) {
            fragmentManager.beginTransaction().replace(R.id.frame_content_main, ReformFragment.newInstance("test")).commit();
        } else if (id == R.id.nav_materials) {
            fragmentManager.beginTransaction().replace(R.id.frame_content_main, MaterialFragment.newInstance("test")).commit();
        } else if (id == R.id.nav_budget) {
            fragmentManager.beginTransaction().replace(R.id.frame_content_main, BudgetFragment.newInstance("test")).commit();
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {

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
}
