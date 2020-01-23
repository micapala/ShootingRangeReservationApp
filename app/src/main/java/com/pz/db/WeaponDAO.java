package com.pz.db;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pz.db.entities.Caliber;
import com.pz.db.entities.Reservation;
import com.pz.db.entities.Weapon;

import java.util.List;

@Dao
public interface WeaponDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWeapon(Weapon word);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCaliber(Caliber caliber);


    @Query("DELETE FROM caliber_t")
    void deleteAllCalibers();

    @Query("DELETE FROM weapon_t")
    void deleteAllWeapons();

    @Query("DELETE FROM weapon_t where weapon_id = :weaponId")
    void deleteWeapon(int weaponId);

    @Query("DELETE FROM caliber_t where caliber_id = :caliberId")
    void deleteCaliber(int caliberId);

    @Query("SELECT * from weapon_t " +
            "join caliber_t on weapon_t.fk_caliber_id = caliber_t.caliber_id" +
            " ORDER BY weaponModel ASC")
    LiveData<List<Weapon>> getAlphabetizedWeaponsLive();

    @Query("SELECT * from caliber_t")
    LiveData<List<Caliber>> getAllCalibersLive();



    @Query("SELECT * from weapon_t " +
            "join caliber_t on weapon_t.fk_caliber_id = caliber_t.caliber_id")
    LiveData<List<Weapon>> getAllWeaponsLive();

    @Query("SELECT * from weapon_t " +
            "join caliber_t on weapon_t.fk_caliber_id = caliber_t.caliber_id" +
            " ORDER BY weaponModel ASC")
    List<Weapon> getAllWeaponsAlphabetized();


    @Query("UPDATE weapon_t SET " +
            "weaponModel = :weaponModel," +
            "weapon_image = :weapon_image," +
            "fk_caliber_id = :fk_caliber_id," +
            "priceForShoot = :priceForShoot " +
            "WHERE weapon_id = :weapon_id")
    void updateWeapon(int weapon_id,byte[] weapon_image,String weaponModel,int fk_caliber_id,double priceForShoot);

}
