package com.example.t00577272.maintenancetracker;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class RepairViewModel extends AndroidViewModel { // Implementation of the ViewModel acting as intermediary between adaptor and dao

    private RepairRepository mRepository;

    private LiveData<List<Repair>> mAllWords;

    public RepairViewModel (Application application) {
        super(application);
        mRepository = new RepairRepository(application);
        mAllWords = mRepository.getAllRepairs();
    }

    LiveData<List<Repair>> getAllRepairs() { return mAllWords; }

    public void insert(Repair repair) { mRepository.insert(repair); }

    public void delete(Repair repair){mRepository.delete(repair);}

}
