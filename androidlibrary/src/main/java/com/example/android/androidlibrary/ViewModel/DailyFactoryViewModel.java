package com.example.android.androidlibrary.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.android.androidlibrary.Database.DailyDatabase;

public class DailyFactoryViewModel extends ViewModelProvider.NewInstanceFactory {

    private DailyDatabase dailyDatabase;
    private int id;

    public DailyFactoryViewModel(DailyDatabase dailyDatabase, int id){
        this.dailyDatabase = dailyDatabase;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GetDailyViewModel(dailyDatabase, id);
    }
}
