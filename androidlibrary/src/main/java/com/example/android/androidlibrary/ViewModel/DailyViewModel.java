package com.example.android.androidlibrary.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.androidlibrary.Database.AppDatabase;
import com.example.android.androidlibrary.Model.Daily;

public class DailyViewModel extends AndroidViewModel{

    private LiveData<Daily[]> dailies;

    public DailyViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        dailies = database.dailyDAO().getAllDailies();
    }

    public LiveData<Daily[]> getAllDailies(){
        return dailies;
    }
}
