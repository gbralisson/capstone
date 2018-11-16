package com.example.android.androidlibrary.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.List;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "daily")
public class Daily{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_daily")
    private int id_daily;

    @ColumnInfo(name = "id_reform")
    private int id_reform;

//    private String material;
//    private String unit;
//    private String value;

    private int quantity;
    @Embedded
    Material material;

    public Daily(){}

    public int getId_daily() {
        return id_daily;
    }

    public void setId_daily(int id_daily) {
        this.id_daily = id_daily;
    }

    public int getId_reform() {
        return id_reform;
    }

    public void setId_reform(int id_reform) {
        this.id_reform = id_reform;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

//    public String getUnit() {

//        return unit;
//    }
//
//    public void setUnit(String unit) {
//        this.unit = unit;
//    }
//
//    public String getValue() {
//        return value;
//    }
//
//    public void setValue(String value) {
//        this.value = value;
//    }
}
