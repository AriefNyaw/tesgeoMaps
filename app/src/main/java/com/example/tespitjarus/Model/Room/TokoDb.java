package com.example.tespitjarus.Model.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Toko.class}, version = 1)
public abstract class TokoDb extends RoomDatabase {

    public abstract Dao dao();
}
