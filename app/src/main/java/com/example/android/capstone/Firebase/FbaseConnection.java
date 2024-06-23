package com.example.android.capstone.Firebase;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FbaseConnection {

    private Context context;
    private static DatabaseReference instance;

    public FbaseConnection(Context context){
        this.context = context;
    }

    public static DatabaseReference getInstance(){
        if(instance == null){
            FirebaseDatabase FInstance = FirebaseDatabase.getInstance();
            FInstance.setPersistenceEnabled(true);
            FbaseConnection.instance = FInstance.getReference();
        }
        return FbaseConnection.instance;
    }

}
