package com.example.android.androidlibrary.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.androidlibrary.Model.Daily;
import com.example.android.androidlibrary.Model.Reform;

@Database(entities = {Daily.class}, version = 1)
public abstract class DailyDatabase extends RoomDatabase{

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "dailyDatabase";
    private static DailyDatabase sInstance;

    public static DailyDatabase getsInstance(Context context){
        if (sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(), DailyDatabase.class, DailyDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract DailyDAO dailyDAO();

}
