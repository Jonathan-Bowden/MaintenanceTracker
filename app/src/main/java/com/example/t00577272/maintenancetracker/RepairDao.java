package com.example.t00577272.maintenancetracker;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RepairDao { // SQL queries for the database

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Repair repair);

    @Query("DELETE FROM repair_table")
    void deleteAll();

    @Query("SELECT * from repair_table ORDER BY dif ASC")
    LiveData<List<Repair>> getAllRepairs();

    @Delete()
    void delete(Repair repair);

}
