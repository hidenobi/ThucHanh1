package com.example.thbuoi1.utils;

import androidx.room.TypeConverter;

import com.example.thbuoi1.models.enums.Subject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ConvertDatabase {
    @TypeConverter
    public String toJson(ArrayList<Subject> subjects) {
        return new Gson().toJson(subjects);
    }

    @TypeConverter
    public ArrayList<Subject> fromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Subject>>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
