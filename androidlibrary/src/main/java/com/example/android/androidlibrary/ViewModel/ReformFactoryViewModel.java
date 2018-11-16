package com.example.android.androidlibrary.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.androidlibrary.Database.AppDatabase;

public class ReformFactoryViewModel extends ViewModelProvider.NewInstanceFactory{

    AppDatabase database;
    int id;

    public ReformFactoryViewModel(AppDatabase database, int id){
        Log.d("teste", String.valueOf(id));
        this.database = database;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GetReformViewModel(database, id);
    }
}
