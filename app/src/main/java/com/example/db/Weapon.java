package com.example.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "weapon_t")
public class Weapon
{
    @PrimaryKey(autoGenerate = true)
    public int weaponPK;

    @ColumnInfo(name = "weaponModel")
    public String weaponModel;

    @ColumnInfo(name = "priceForRental")
    public int priceForRental;
    public Weapon(String model){
        this.weaponModel=model;
    }

}
