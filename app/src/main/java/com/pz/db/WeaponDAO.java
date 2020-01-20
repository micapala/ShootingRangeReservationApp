package com.pz.db;

import androidx.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pz.db.entities.Caliber;
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

    @Query("SELECT * from weapon_t " +
            "join caliber_t on weapon_t.fk_caliber_id = caliber_t.caliber_id" +
            " ORDER BY weaponModel ASC")
    LiveData<List<Weapon>> getAlphabetizedWeapons();

    @Query("SELECT * from caliber_t")
    LiveData<List<Caliber>> getAllCalibers();

    @Query("SELECT * from weapon_t " +
            "join caliber_t on weapon_t.fk_caliber_id = caliber_t.caliber_id")
    LiveData<List<Weapon>> getAllWeapons();

    @Query("SELECT * from caliber_t where caliber_name = :name")
    LiveData<List<Caliber>> getCaliberByName(String name);

    @Query("UPDATE weapon_t SET " +
            "weaponModel = :weaponModel," +
            "weapon_image = :weapon_image," +
            "fk_caliber_id = :fk_caliber_id," +
            "priceForShoot = :priceForShoot " +
            "WHERE weapon_id = :weapon_id")
    void updateWeapon(int weapon_id,byte[] weapon_image,String weaponModel,int fk_caliber_id,int priceForShoot);

}
