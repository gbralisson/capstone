package com.example.android.androidlibrary.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.android.androidlibrary.Model.Daily;

@Dao
public interface DailyDAO {

    @Query("SELECT * FROM daily")
    LiveData<Daily[]> getAllDailies();

    @Query("SELECT * FROM daily WHERE id_reform=:id_reform")
    LiveData<Daily[]> getDailiesFromReform(int id_reform);

    @Insert
    void insertDaily(Daily daily);

    @Delete
    void deleteDaily(Daily daily);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDaily(Daily daily);

}
