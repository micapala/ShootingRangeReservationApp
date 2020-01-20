package com.pz.db;

import android.app.Application;

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

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ShootingRangeRepository(Application application) {
        ShootingRangeDb db = ShootingRangeDb.getDatabase(application);
        mWeaponDAO = db.weaponDAO();
        mReservationDAO = db.reservationDAO();
        mAllWeapons = mWeaponDAO.getAlphabetizedWeapons();
        mAllCalibers = mWeaponDAO.getAllCalibers();
        mAllReservations = mReservationDAO.getAllReservationsLive();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Weapon>> getAllWeapons() {
        return mAllWeapons;
    }
    public LiveData<List<Caliber>> getAllCalibers() {
        return mAllCalibers;
    }
    public LiveData<List<Reservation>> getAllReservations() {
        return mAllReservations;
    }
    public List<Reservation> getAllR() {
        return mReservationDAO.getAllReservations();
    }
    public LiveData<List<Reservation>> getReservationsFromDay(long date){
        return mReservationDAO.getDateReservations(date);
    }


    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
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
    public void updateWeapon(int weapon_id,byte[] weapon_image,String weaponModel,int fk_caliber_id,double priceForShoot) {
        ShootingRangeDb.databaseWriteExecutor.execute(() -> {
            mWeaponDAO.updateWeapon(weapon_id,weapon_image,weaponModel,fk_caliber_id,priceForShoot);
        });
    }


}
