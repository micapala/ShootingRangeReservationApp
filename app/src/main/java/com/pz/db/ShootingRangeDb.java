package com.pz.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pz.db.entities.Caliber;
import com.pz.db.entities.Reservation;
import com.pz.db.entities.Track;
import com.pz.db.entities.Weapon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Weapon.class,Caliber.class, Reservation.class, Track.class}, version = 3, exportSchema = false)
public abstract class ShootingRangeDb extends RoomDatabase {
    public abstract WeaponDAO weaponDAO();


    public abstract ReservationDAO reservationDAO();

    private static volatile ShootingRangeDb INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;


    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ShootingRangeDb getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ShootingRangeDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShootingRangeDb.class, "ShootingRangeDb")
                           // .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            //.fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            databaseWriteExecutor.execute(() -> {

                WeaponDAO weaponDAO = INSTANCE.weaponDAO();
                ReservationDAO reservationDAO = INSTANCE.reservationDAO();
                reservationDAO.deleteAllReservations();
                weaponDAO.deleteAllCalibers();

                Caliber cal1 = new Caliber("9 mm");
                Caliber cal2 = new Caliber("5,56 mm");
                Caliber cal3 = new Caliber("7,65 mm");
                Caliber cal4 = new Caliber(".22LR");
                Caliber cal5 = new Caliber(".300");
                Caliber cal6 = new Caliber(".45 ACP");
                Caliber cal7 = new Caliber(".357 MAGNUM");
                Caliber cal8 = new Caliber(".44 MAGNUM");
                Caliber cal9 = new Caliber("7,62");


                weaponDAO.insertCaliber(cal1);
                weaponDAO.insertCaliber(cal2);
                weaponDAO.insertCaliber(cal3);
                weaponDAO.insertCaliber(cal4);
                weaponDAO.insertCaliber(cal5);
                weaponDAO.insertCaliber(cal6);
                weaponDAO.insertCaliber(cal7);
                weaponDAO.insertCaliber(cal8);
                weaponDAO.insertCaliber(cal9);
            });
        }
    };

}
