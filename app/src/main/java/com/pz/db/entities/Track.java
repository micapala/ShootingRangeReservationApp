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

    public Track(int track_length){
        this.track_length = track_length;
    }
}
