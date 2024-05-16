package com.example.thbuoi1.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.thbuoi1.models.ItemData;

import java.util.List;

@Dao
public interface ItemDataDao {
    @Query("SELECT * FROM itemData")
    List<ItemData> getList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveItemData(ItemData itemData);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItemData(ItemData itemData);

    @Delete
    void deleteItemData(ItemData itemData);

}
