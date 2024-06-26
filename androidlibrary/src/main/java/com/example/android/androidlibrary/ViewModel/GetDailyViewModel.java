package com.example.android.androidlibrary.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.androidlibrary.Database.AppDatabase;
import com.example.android.androidlibrary.Model.Daily;

public class GetDailyViewModel extends ViewModel{

    private LiveData<Daily[]> dailies;

    public GetDailyViewModel(AppDatabase dailyDatabase, int id){
        dailies = dailyDatabase.dailyDAO().getDailiesFromReform(id);
    }

    public LiveData<Daily[]> getDailies() {
        return dailies;
    }
}
