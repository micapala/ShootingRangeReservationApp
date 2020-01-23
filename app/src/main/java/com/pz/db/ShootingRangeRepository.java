package com.pz.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.pz.db.entities.Caliber;
import com.pz.db.entities.Reservation;
import com.pz.db.entities.Weapon;

import java.util.List;

public class ShootingRangeRepository {
    private WeaponDAO mWeaponDAO;
    private ReservationDAO mReservationDAO;
    private LiveData<List<Weapon>> mAllWeapons;
    private LiveData<List<Caliber>> mAllCalibers;
    private LiveData<List<Reservation>> mAllReservations;
    private ShootingRangeDb db;


    public ShootingRangeRepository(Application application) {
        db = ShootingRangeDb.getDatabase(application);
        mWeaponDAO = db.weaponDAO();
        mReservationDAO = db.reservationDAO();
        mAllWeapons = mWeaponDAO.getAlphabetizedWeaponsLive();
        mAllCalibers = mWeaponDAO.getAllCalibersLive();
        mAllReservations = mReservationDAO.getAllReservationsLive();
    }

    public LiveData<List<Weapon>> getAllWeaponsLive() {
        return mAllWeapons;
    }

    public List<Weapon> getAllWeaponsAlphabetized() {
        List<Weapon> returnList = null;
        try {
            returnList = new GetWeaponsAlphabetizedAsyncTask().execute().get();
        }
        catch(Exception e){}
        return returnList;
    }
    public LiveData<List<Caliber>> getAllCalibersLive() {
        return mAllCalibers;
    }
    public LiveData<List<Reservation>> getAllReservationsLive() {
        return mAllReservations;
    }
    public LiveData<List<Reservation>> getReservationsFromDay(long date) {
        return mReservationDAO.getReservationsFromDay(date);
    }
    public List<Reservation> getAllReservations() {
        List<Reservation> returnList = null;
        try {
            returnList = new GetReservationsAsyncTask().execute().get();
        }
        catch(Exception e){}
        return returnList;
    }


    public void insertWeapon(Weapon word) {
        ShootingRangeDb.databaseWriteExecutor.execute(() -> {
            mWeaponDAO.insertWeapon(word);
        });
    }
    public void insertCaliber(Caliber caliber) {
        ShootingRangeDb.databaseWriteExecutor.execute(() -> {
            mWeaponDAO.insertCaliber(caliber);
        });
    }
    public void insertReservation(Reservation reservation) {
        ShootingRangeDb.databaseWriteExecutor.execute(() -> {
            mReservationDAO.insertReservation(reservation);
        });
    }
    public void deleteWeapon(int weapon_id){
        ShootingRangeDb.databaseWriteExecutor.execute(() -> {
            mWeaponDAO.deleteWeapon(weapon_id);
        });
    }
    public void deleteCaliber(int caliber_id){
        ShootingRangeDb.databaseWriteExecutor.execute(() -> {
            mWeaponDAO.deleteCaliber(caliber_id);
        });
    }
    public void updateWeapon(int weapon_id,byte[] weapon_image,String weaponModel,int fk_caliber_id,double priceForShoot) {
        ShootingRangeDb.databaseWriteExecutor.execute(() -> {
            mWeaponDAO.updateWeapon(weapon_id,weapon_image,weaponModel,fk_caliber_id,priceForShoot);
        });
    }

    private class GetWeaponsAlphabetizedAsyncTask extends AsyncTask<Void, Void,List<Weapon>>
    {
        @Override
        protected List<Weapon> doInBackground(Void... url) {
            return db.weaponDAO().getAllWeaponsAlphabetized();
        }
    }

    private class GetReservationsAsyncTask extends AsyncTask<Void, Void,List<Reservation>>
    {
        @Override
        protected List<Reservation> doInBackground(Void... url) {
            return db.reservationDAO().getAllReservations();
        }
    }


}
