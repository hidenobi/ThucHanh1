package com.example.thbuoi1;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Item implements Serializable {
    public String name;
    public String birthday;
    public boolean isDefender = false;
    public boolean isMidfielder = false;
    public boolean isStriker = false;
    @ColorRes
    public int color;
    public long id = System.currentTimeMillis();

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Item) {
            return this.id == ((Item) obj).id;
        } else {
            return false;
        }
    }

    public Item(String name, String birthday, boolean isDefender, boolean isMidfielder, boolean isStriker, int color) {
        this.name = name;
        this.birthday = birthday;
        this.isDefender = isDefender;
        this.isMidfielder = isMidfielder;
        this.isStriker = isStriker;
        this.color = color;
    }

    public Item(String name, String birthday, boolean isDefender, boolean isMidfielder, boolean isStriker, int color, long id) {
        this.name = name;
        this.birthday = birthday;
        this.isDefender = isDefender;
        this.isMidfielder = isMidfielder;
        this.isStriker = isStriker;
        this.color = color;
        this.id = id;
    }
}

