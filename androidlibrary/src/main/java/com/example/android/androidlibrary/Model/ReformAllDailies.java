package com.example.android.androidlibrary.Model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;

import java.util.List;

public class ReformAllDailies{

    @Embedded
    private Reform reform;

    @Relation(parentColumn = "id", entityColumn = "id_reform", entity = Daily.class)
    private List<Daily> dailies;

    public Reform getReform() {
        return reform;
    }

    public void setReform(Reform reform) {
        this.reform = reform;
    }

    public List<Daily> getDailies() {
        return dailies;
    }

    public void setDailies(List<Daily> dailies) {
        this.dailies = dailies;
    }

}
