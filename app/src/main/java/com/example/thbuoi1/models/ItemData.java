package com.example.thbuoi1.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.thbuoi1.models.enums.Scope;
import com.example.thbuoi1.models.enums.Subject;

import java.util.ArrayList;

@Entity(tableName = "itemData")
public class ItemData {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "author")
    public String author;
    @ColumnInfo(name = "scope")
    public Scope scope;
    @ColumnInfo(name = "subject")
    public ArrayList<Subject> subjects;
    @ColumnInfo(name = "rating")
    public float rating;

    public ItemData(int uid, String name, String author, Scope scope, ArrayList<Subject> subjects, float rating) {
        this.uid = uid;
        this.name = name;
        this.author = author;
        this.scope = scope;
        this.subjects = subjects;
        this.rating = rating;
    }

    public ItemData() {
    }

    public ItemData(String name, String author, Scope scope, ArrayList<Subject> subjects, float rating) {
        this.name = name;
        this.author = author;
        this.scope = scope;
        this.subjects = subjects;
        this.rating = rating;
    }
}




