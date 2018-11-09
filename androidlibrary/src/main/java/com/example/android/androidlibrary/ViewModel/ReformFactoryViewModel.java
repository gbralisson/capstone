package com.example.android.androidlibrary.ViewModel;

import android.arch.lifecycle.ViewModelProvider;

import com.example.android.androidlibrary.Database.AppDatabase;

public class ReformFactoryViewModel extends ViewModelProvider.NewInstanceFactory{

    private AppDatabase database;
    private int id;

    public ReformFactoryViewModel(AppDatabase database, int id){
        this.database = database;
        this.id = id;
    }

}
