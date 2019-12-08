package com.example.db;

import androidx.lifecycle.LiveData;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface WeaponDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Weapon word);

    @Query("DELETE FROM weapon_t")
    void deleteAll();

    @Query("SELECT * from weapon_t ORDER BY weaponModel ASC")
    LiveData<List<Weapon>> getAlphabetizedWords();
}
