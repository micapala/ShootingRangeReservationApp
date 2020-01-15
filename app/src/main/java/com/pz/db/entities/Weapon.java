package com.pz.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "weapon_t", foreignKeys = @ForeignKey(entity = Caliber.class,
                                                            parentColumns ="caliber_id",
                                                            childColumns = "fk_caliber_id",
                                                            onDelete = CASCADE))
public class Weapon
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "weapon_id")
    public int weaponPK;

    @ColumnInfo(name = "weaponModel")
    public String weaponModel;

    @ColumnInfo(name = "fk_caliber_id")
    public int fk_caliber_id;

    @ColumnInfo(name = "priceForShoot")
    public int priceForShoot;
    public Weapon(String weaponModel,int fk_caliber_id,int priceForShoot){
        this.weaponModel=weaponModel;
        this.fk_caliber_id = fk_caliber_id;
        this.priceForShoot = priceForShoot;
    }

}
