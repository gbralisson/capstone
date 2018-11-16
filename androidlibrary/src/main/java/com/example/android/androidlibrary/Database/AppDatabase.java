package com.example.android.androidlibrary.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.android.androidlibrary.Model.Daily;
import com.example.android.androidlibrary.Model.Material;
import com.example.android.androidlibrary.Model.Reform;

@Database(entities = {Reform.class, Material.class, Daily.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "database";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context){
        if (sInstance == null){
            Log.d("teste", "criou nova instancia");
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract ReformDAO reformDAO();

    public abstract MaterialDAO materialDAO();

    public abstract DailyDAO dailyDAO();

}
