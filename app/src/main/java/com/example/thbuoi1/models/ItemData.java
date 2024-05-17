package com.example.thbuoi1.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.thbuoi1.models.enums.Scope;

@Entity(tableName = "itemData")
public class ItemData {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "date")
    public String date;
    @ColumnInfo(name = "scope")
    public Scope scope;
    @ColumnInfo(name = "active")
    public Boolean active;
    @ColumnInfo(name = "hocPhi")
    public float hocPhi;

    public ItemData() {
    }

    public ItemData(int uid, String name, String date, Scope scope, Boolean active, float hocPhi) {
        this.uid = uid;
        this.name = name;
        this.date = date;
        this.scope = scope;
        this.active = active;
        this.hocPhi = hocPhi;
    }

    public ItemData(String name, String date, Scope scope, Boolean active, float hocPhi) {
        this.name = name;
        this.date = date;
        this.scope = scope;
        this.active = active;
        this.hocPhi = hocPhi;
    }
}




