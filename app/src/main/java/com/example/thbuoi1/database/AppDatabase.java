package com.example.thbuoi1.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.thbuoi1.models.ItemData;
import com.example.thbuoi1.utils.ConvertDatabase;

import kotlin.jvm.Synchronized;

@Database(entities = {ItemData.class}, version = 1)
@TypeConverters(ConvertDatabase.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDataDao itemDataDao();

    private static AppDatabase appDatabase;

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase = Room
                    .databaseBuilder(context, AppDatabase.class, "th2-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return appDatabase;
    }


}
