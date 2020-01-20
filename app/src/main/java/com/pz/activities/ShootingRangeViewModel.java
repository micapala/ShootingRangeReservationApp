package com.pz.activities;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pz.db.ShootingRangeRepository;
import com.pz.db.entities.Caliber;
import com.pz.db.entities.Weapon;

import java.util.List;

public class ShootingRangeViewModel extends AndroidViewModel {
    private ShootingRangeRepository mRepository;

    private LiveData<List<Weapon>> mAllWeapons;
    private LiveData<List<Caliber>> mAllCalibers;

    public ShootingRangeViewModel(Application application){
        super(application);
        mRepository = new ShootingRangeRepository(application);
        mAllWeapons = mRepository.getAllWeapons();
        mAllCalibers = mRepository.getAllCalibers();
    }
    public LiveData<List<Weapon>> getAllWeapons() { return mAllWeapons; }

    public LiveData<List<Caliber>> getAllCalibers() {

        if (mAllCalibers == null) {
            mAllCalibers =mRepository.getAllCalibers();
        }

        return mAllCalibers;
    }


    public void insertWeapon(Weapon word) { mRepository.insertWeapon(word); }
    public void insertCaliber(Caliber caliber) { mRepository.insertCaliber(caliber); }
}
