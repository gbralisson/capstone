package com.example.android.androidlibrary.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@Entity(tableName = "reform")
public class Reform implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String room;
    private String days;
    private String total_spent;

    public Reform(){}

    public Reform(Parcel parcel){
        room = parcel.readString();
        days = parcel.readString();
        total_spent = parcel.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getTotal_spent() {
        return total_spent;
    }

    public void setTotal_spent(String total_spent) {
        this.total_spent = total_spent;
    }

    public static final Parcelable.Creator<Reform> CREATOR = new Parcelable.Creator<Reform>(){

        @Override
        public Reform createFromParcel(Parcel parcel) {
            return new Reform(parcel);
        }

        @Override
        public Reform[] newArray(int i) {
            return new Reform[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(room);
        parcel.writeString(days);
        parcel.writeString(total_spent);
    }

}
