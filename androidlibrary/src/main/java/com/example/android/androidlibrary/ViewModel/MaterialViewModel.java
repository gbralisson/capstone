package com.example.android.androidlibrary.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.androidlibrary.Database.MaterialDatabase;
import com.example.android.androidlibrary.Model.Material;

public class MaterialViewModel extends AndroidViewModel{

    private LiveData<Material[]> materials;

    public MaterialViewModel(@NonNull Application application) {
        super(application);

        MaterialDatabase materialDatabase = MaterialDatabase.getsInstance(this.getApplication());
        materialDatabase.materialDAO().loadAllmaterials();
    }

    public LiveData<Material[]> getMaterials(){
        return materials;
    }

}
