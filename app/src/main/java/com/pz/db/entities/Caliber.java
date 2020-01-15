package com.pz.db.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "caliber_t")
public class Caliber {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "caliber_id",index = true)
    public int caliberId;
    @ColumnInfo(name = "caliber_name")
    public String caliberName;


    public Caliber(String caliberName){
        this.caliberName = caliberName;
    }
}
