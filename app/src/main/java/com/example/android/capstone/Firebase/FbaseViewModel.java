package com.example.android.capstone.Firebase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class FbaseViewModel extends AndroidViewModel{

    private FbaseRepository fbaseRepository;

    public FbaseViewModel(@NonNull Application application) {
        super(application);
        fbaseRepository = new FbaseRepository();
    }

    public LiveData<List<String>> getUnits(){
        return fbaseRepository.getUnits();
    }
}
