package com.example.thbuoi1;

import android.app.Application;

import com.example.thbuoi1.database.AppDatabase;

public class MyApplication extends Application {
    public static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = AppDatabase.getAppDatabase(MyApplication.this);
    }
}
