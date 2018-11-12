package com.example.android.androidlibrary.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.androidlibrary.Model.Material;

@Database(entities = {Material.class}, version = 1, exportSchema = false)
public abstract class MaterialDatabase extends RoomDatabase{

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "materialDatabase";
    private static MaterialDatabase sInstance;

    public static MaterialDatabase getsInstance(Context context){
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), MaterialDatabase.class, MaterialDatabase.DATABASE_NAME)
                    .build();
        }
        return sInstance;
    }

    public abstract MaterialDAO materialDAO();

}
