package com.pz;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.pz.db.ShootingRangeRepository;
import com.pz.db.entities.Caliber;
import com.pz.db.entities.Reservation;
import com.pz.db.entities.Track;
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
    private LiveData<List<Track>> mAllTracks;

    public ShootingRangeViewModel(Application application) {
        super(application);
        mRepository = new ShootingRangeRepository(application);
        mAllWeapons = mRepository.getAllWeaponsLive();
        mAllCalibers = mRepository.getAllCalibersLive();
        mAllReservations = mRepository.getAllActiveReservationsLive();
        mAllTracks = mRepository.getAllTracksLive();
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

    public LiveData<List<Reservation>> getAllActiveReservationsLive() {

        if (mAllReservations == null) {
            mAllReservations = mRepository.getAllActiveReservationsLive();
        }
        return mAllReservations;
    }
    public LiveData<List<Track>> getAllTracksLive(){
        if(mAllTracks ==null){
            mAllTracks = mRepository.getAllTracksLive();
        }
        return mAllTracks;
    }

    public List<Track> getAllTracks(){
        return mRepository.getAllTracks();
    }
    public LiveData<List<Reservation>> getActiveReservationsFromDay(long date) {
        return mRepository.getActiveReservationsFromDay(date);
    }

    public List<Reservation> getAllActiveReservations() {

        return mRepository.getAllActiveReservations();
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
    public void updateCaliber(int caliber_id,String caliber_name){
        mRepository.updateCaliber(caliber_id,caliber_name);
    }
    public void updateTrack(int track_id,String track_name,int track_length){
        mRepository.updateTrack(track_id,track_name,track_length);
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
    public void insertTrack(Track t){
        mRepository.insertTrack(t);
    }

    public void cancelReservation(int reservation_id){
        mRepository.cancelReservation(reservation_id);
    }




}
