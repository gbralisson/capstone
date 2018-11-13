package com.example.android.capstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.androidlibrary.Model.Reform;

public class ReformDetailActivity extends AppCompatActivity {

    private static final String TAG = "reform_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reform_detail);

        if (getIntent() != null){
            if (getIntent().hasExtra(TAG)){
                Reform reform = getIntent().getParcelableExtra(TAG);
                Log.d("teste", reform.getRoom());
            }
        }

    }
}
