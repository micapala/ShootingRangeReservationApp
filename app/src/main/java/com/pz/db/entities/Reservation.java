package com.pz.db.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "reservation_t",foreignKeys = @ForeignKey(entity = Track.class,
        parentColumns ="track_id",
        childColumns = "fk_track_id",
        onDelete = CASCADE))
public class Reservation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reservation_id",index = true)
    public int reservation_id;

    public String customer_name;

    public String customer_surname;

    public int fk_track_id;

    public long reservation_date;

    public int reservation_hour;

    public int number_of_Hours;

    public boolean is_active;

    public Reservation(String customer_name,String customer_surname,long reservation_date,int reservation_hour,int number_of_Hours,int fk_track_id){
        this.customer_name = customer_name;
        this.customer_surname = customer_surname;
        this.reservation_date = reservation_date;
        this.reservation_hour = reservation_hour;
        this.number_of_Hours = number_of_Hours;
        this.fk_track_id = fk_track_id;
        this.is_active = true;
    }


}
