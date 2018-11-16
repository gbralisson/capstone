package com.example.android.androidlibrary.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.androidlibrary.Database.AppDatabase;
import com.example.android.androidlibrary.Model.ReformAllDailies;

public class GetReformViewModel extends ViewModel{

    private LiveData<ReformAllDailies> reformAllDailiesLiveData;

    public GetReformViewModel(AppDatabase database, int id){
        reformAllDailiesLiveData = database.reformDAO().loadReformAllDailies(id);
    }

    public LiveData<ReformAllDailies> getReformAllDailiesLiveData() {
        return reformAllDailiesLiveData;
    }
}
