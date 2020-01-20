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
    public LiveData<List<Weapon>> getAllWeapons() {
        if(mAllWeapons==null){
            mAllWeapons = mRepository.getAllWeapons();
        }
        return mAllWeapons;
    }

    public LiveData<List<Caliber>> getAllCalibers() {

        if (mAllCalibers == null) {
            mAllCalibers =mRepository.getAllCalibers();
        }

        return mAllCalibers;
    }

    public void updateWeapon(int weapon_id,byte[] weapon_image,String weaponModel,int fk_caliber_id,double priceForShoot){
        mRepository.updateWeapon(weapon_id,weapon_image,weaponModel,fk_caliber_id,priceForShoot);
    }



    public void insertWeapon(Weapon word) { mRepository.insertWeapon(word); }
    public void insertCaliber(Caliber caliber) { mRepository.insertCaliber(caliber); }
}
