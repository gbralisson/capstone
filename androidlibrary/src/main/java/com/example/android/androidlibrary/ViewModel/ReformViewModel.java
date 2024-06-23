package com.example.android.androidlibrary.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.androidlibrary.Database.AppDatabase;
import com.example.android.androidlibrary.Model.Reform;
import com.example.android.androidlibrary.Model.ReformAllDailies;

import java.util.List;

public class ReformViewModel extends AndroidViewModel{

    private LiveData<Reform[]> reforms;
    private LiveData<List<ReformAllDailies>> reformAllDailiesLiveData;

    public ReformViewModel(@NonNull Application application) {
        super(application);

        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        reforms = database.reformDAO().loadAllreforms();
        reformAllDailiesLiveData = database.reformDAO().loadAllReformAllDailies();
    }

    public LiveData<Reform[]> getReforms() {
        return reforms;
    }

    public LiveData<List<ReformAllDailies>> getReformAllDailiesLiveData() {
        return reformAllDailiesLiveData;
    }
}
