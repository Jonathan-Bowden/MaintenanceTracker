package com.example.t00577272.maintenancetracker;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Repair.class}, version = 1, exportSchema = false)
public abstract class RepairRoomDatabase extends RoomDatabase { // Singleton RoomDatabase with Repairs as entries
    public abstract RepairDao repairDao();


    private static volatile RepairRoomDatabase INSTANCE;

    static RepairRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RepairRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RepairRoomDatabase.class, "repair_database").addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    //new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final RepairDao mDao;

        PopulateDbAsync(RepairRoomDatabase db) {
            mDao = db.repairDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //mDao.deleteAll();
            Repair repair = new Repair("Hello");
            mDao.insert(repair);
            repair = new Repair("World");
            mDao.insert(repair);
            return null;
        }
    }


}
