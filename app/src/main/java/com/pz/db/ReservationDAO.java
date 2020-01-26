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

    @Query("SELECT * from reservation_t where is_active = 1")
    LiveData<List<Reservation>> getAllActiveReservationsLive();

    @Query("SELECT * from reservation_t where is_active = 1")
    List<Reservation> getAllActiveReservations();

    @Query("SELECT * from reservation_t where reservation_date = :date and is_active = 1 order by reservation_hour")
    LiveData<List<Reservation>> getReservationsFromDay(long date);

    @Query("UPDATE reservation_t set is_active = 0 where reservation_id =:reservation_id")
    void cancelReservation(int reservation_id);

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

    @Query("UPDATE track_t SET track_name = :track_name , track_length = :track_length where track_id =:track_id")
    void updateTrack(int track_id,String track_name,int track_length);

}
