package com.example.t00577272.maintenancetracker;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RepairRepository { // Instantiate dao and create asynctasks to manipulate data

    private RepairDao mRepairDao;
    private LiveData<List<Repair>> mAllWords;

    RepairRepository(Application application) {
        RepairRoomDatabase db = RepairRoomDatabase.getDatabase(application);
        mRepairDao = db.repairDao();
        mAllWords = mRepairDao.getAllRepairs();
    }

    LiveData<List<Repair>> getAllRepairs() {
        return mAllWords;
    }

    public void delete(Repair repair){new deleteAsyncTask(mRepairDao).execute(repair);}

    private static class deleteAsyncTask extends AsyncTask<Repair, Void, Void> {

        private RepairDao mAsyncTaskDao;

        deleteAsyncTask(RepairDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Repair... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }


    public void insert (Repair repair) {
        new insertAsyncTask(mRepairDao).execute(repair);
    } //

    private static class insertAsyncTask extends AsyncTask<Repair, Void, Void> {

        private RepairDao mAsyncTaskDao;

        insertAsyncTask(RepairDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Repair... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
