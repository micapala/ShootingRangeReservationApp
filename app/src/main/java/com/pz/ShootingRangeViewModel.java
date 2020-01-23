package com.pz;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pz.db.ShootingRangeRepository;
import com.pz.db.entities.Caliber;
import com.pz.db.entities.Reservation;
import com.pz.db.entities.Weapon;

import java.util.List;

public class ShootingRangeViewModel extends AndroidViewModel {

    public static int openingHour = 8;
    public static int closeHour = 18;
    public static int numberOfTracks = 1;
    private ShootingRangeRepository mRepository;

    private LiveData<List<Weapon>> mAllWeapons;
    private LiveData<List<Caliber>> mAllCalibers;
    private LiveData<List<Reservation>> mAllReservations;

    public ShootingRangeViewModel(Application application) {
        super(application);
        mRepository = new ShootingRangeRepository(application);
        mAllWeapons = mRepository.getAllWeaponsLive();
        mAllCalibers = mRepository.getAllCalibersLive();
        mAllReservations = mRepository.getAllReservationsLive();
    }

    public LiveData<List<Weapon>> getAllWeaponsLive() {
        if (mAllWeapons == null) {
            mAllWeapons = mRepository.getAllWeaponsLive();
        }
        return mAllWeapons;
    }
    public List<Weapon> getAllWeapons(){
        return mRepository.getAllWeaponsAlphabetized();
    }

    public LiveData<List<Caliber>> getAllCalibersLive() {
        if (mAllCalibers == null) {
            mAllCalibers = mRepository.getAllCalibersLive();
        }
        return mAllCalibers;
    }

    public LiveData<List<Reservation>> getAllReservationsLive() {

        if (mAllReservations == null) {
            mAllReservations = mRepository.getAllReservationsLive();
        }
        return mAllReservations;
    }
    public LiveData<List<Reservation>> getReservationsFromDay(long date) {
        return mRepository.getReservationsFromDay(date);
    }

    public List<Reservation> getAllReservations() {

        return mRepository.getAllReservations();
    }

    public void deleteWeapon(int weapon_id) {
        mRepository.deleteWeapon(weapon_id);
    }

    public void deleteCaliber(int caliber_id) {
        mRepository.deleteCaliber(caliber_id);
    }

    public void updateWeapon(int weapon_id, byte[] weapon_image, String weaponModel, int fk_caliber_id, double priceForShoot) {
        mRepository.updateWeapon(weapon_id, weapon_image, weaponModel, fk_caliber_id, priceForShoot);
    }

    public void insertWeapon(Weapon word) {
        mRepository.insertWeapon(word);
    }

    public void insertCaliber(Caliber caliber) {
        mRepository.insertCaliber(caliber);
    }

    public void insertReservation(Reservation reservation) {
        mRepository.insertReservation(reservation);
    }




}
