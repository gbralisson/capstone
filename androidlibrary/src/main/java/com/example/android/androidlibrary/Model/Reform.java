package com.example.android.androidlibrary.Model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "reform")
public class Reform {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String room;
    private String days;
    private String total_spent;
//    private List<Material> materials;

    public Reform(){}

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

//    public List<Material> getMaterials() {
//        return materials;
//    }
//
//    public void setMaterials(List<Material> materials) {
//        this.materials = materials;
//    }
}
