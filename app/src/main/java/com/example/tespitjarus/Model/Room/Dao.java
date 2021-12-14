package com.example.tespitjarus.Model.Room;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@androidx.room.Dao
public interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertToko(Toko toko);

    @Query("SELECT * FROM toko")
    Toko[] readDataToko();
}
