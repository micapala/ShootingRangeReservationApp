package com.pz.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "track_t")
public class Track {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "track_id",index = true)
    public int track_id;

    public int track_length;

    public String track_name;

    public Track(String track_name,int track_length){
        this.track_length = track_length;
        this.track_name = track_name;
    }
}
