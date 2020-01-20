package com.pz;

import android.app.Application;
import android.os.AsyncTask;

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
        mAllWeapons = mRepository.getAllWeapons();
        mAllCalibers = mRepository.getAllCalibers();
        mAllReservations = mRepository.getAllReservations();
    }

    public LiveData<List<Weapon>> getAllWeapons() {
        if (mAllWeapons == null) {
            mAllWeapons = mRepository.getAllWeapons();
        }
        return mAllWeapons;
    }

    public LiveData<List<Caliber>> getAllCalibers() {
        if (mAllCalibers == null) {
            mAllCalibers = mRepository.getAllCalibers();
        }
        return mAllCalibers;
    }

    public List<Reservation> getReservationsOfDay(long day) {
        List<Reservation> reservationList = mAllReservations.getValue();
        if(reservationList!=null) {
            for (Reservation r : reservationList) {
                if (r.reservation_date != day) reservationList.remove(r);
            }
        }
        return reservationList;
    }

    public LiveData<List<Reservation>> getAllReservations() {

        if (mAllReservations == null) {
            mAllReservations = mRepository.getAllReservations();
        }
        return mAllReservations;
    }

    public List<Reservation> getAllR() {

        return mRepository.getAllR();
    }

    public void deleteWeapon(int weapon_id) {
        mRepository.deleteWeapon(weapon_id);
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
