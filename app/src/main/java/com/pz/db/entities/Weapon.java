package com.pz.db.entities;

import android.graphics.Bitmap;
import android.net.Uri;

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

    @ColumnInfo(name = "weapon_image")
    public byte[] weapon_image;

    @ColumnInfo(name = "weaponModel")
    public String weaponModel;

    @ColumnInfo(name = "fk_caliber_id",index = true)
    public int fk_caliber_id;

    @ColumnInfo(name = "priceForShoot")
    public double priceForShoot;

    public Weapon(byte[] weapon_image,String weaponModel,int fk_caliber_id,double priceForShoot){
        this.weapon_image = weapon_image;
        this.weaponModel=weaponModel;
        this.fk_caliber_id = fk_caliber_id;
        this.priceForShoot = priceForShoot;
    }

}
