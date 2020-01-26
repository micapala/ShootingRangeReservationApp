package com.pz.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pz.db.entities.Reservation;
import com.pz.db.entities.Track;

import java.util.List;

@Dao
public interface ReservationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertReservation(Reservation reservation);

    @Query("SELECT * from reservation_t")
    LiveData<List<Reservation>> getAllReservationsLive();

    @Query("SELECT * from reservation_t")
    List<Reservation> getAllReservations();

    @Query("SELECT * from reservation_t where reservation_date = :date order by reservation_date")
    LiveData<List<Reservation>> getReservationsFromDay(long date);

    @Query("DELETE FROM reservation_t")
    void deleteAllReservations();

    @Query("SELECT * from track_t")
    LiveData<List<Track>> getAllTracksLive();

    @Query("SELECT * from track_t")
    List<Track> getAllTracks();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTrack(Track track);

    @Query("DELETE FROM track_t")
    void deleteAllTracks();
}
