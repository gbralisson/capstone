package com.example.android.androidlibrary.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.androidlibrary.Model.Material;

@Dao
public interface MaterialDAO {

    @Query("SELECT * FROM material ORDER BY id")
    LiveData<Material[]> loadAllmaterials();

    @Query("SELECT * FROM material WHERE id = :id")
    LiveData<Material> loadMaterial(int id);

    @Insert
    void insertMaterial(Material material);

    @Delete
    void deleteMaterial(Material material);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMaterial(Material material);

}
