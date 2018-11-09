package com.example.android.androidlibrary.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.androidlibrary.Model.Reform;

@Dao
public interface ReformDAO {

    @Query("SELECT * FROM reform ORDER BY id")
    LiveData<Reform[]> loadAllreforms();

    @Query("SELECT * FROM reform WHERE id = :id")
    LiveData<Reform> loadReform(int id);

    @Insert
    void insertReform(Reform reform);

    @Delete
    void deleteReform(Reform reform);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateReform(Reform reform);

}
