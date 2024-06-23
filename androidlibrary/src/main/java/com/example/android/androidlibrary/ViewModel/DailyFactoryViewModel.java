package com.example.android.androidlibrary.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.androidlibrary.Database.AppDatabase;

public class DailyFactoryViewModel extends ViewModelProvider.NewInstanceFactory {

    private AppDatabase dailyDatabase;
    private int id;

    public DailyFactoryViewModel(AppDatabase dailyDatabase, int id){
        this.dailyDatabase = dailyDatabase;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GetDailyViewModel(dailyDatabase, id);
    }
}
