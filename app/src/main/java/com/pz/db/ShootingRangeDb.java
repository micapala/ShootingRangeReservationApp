package com.pz.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.pz.db.entities.Caliber;
import com.pz.db.entities.Weapon;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Weapon.class,Caliber.class}, version = 1, exportSchema = false)
public abstract class ShootingRangeDb extends RoomDatabase {
    public abstract WeaponDAO weaponDAO();

    private static volatile ShootingRangeDb INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;


    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public synchronized static ShootingRangeDb getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = getDatabase(context);
        }
        return INSTANCE;
    }


    static ShootingRangeDb getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ShootingRangeDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShootingRangeDb.class, "ShootingRangeDb")
                            .addCallback(sRoomDatabaseCallback)
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
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                WeaponDAO dao = INSTANCE.weaponDAO();
                dao.deleteAllWeapons();
                dao.deleteAllCalibers();

                Caliber cal1 = new Caliber("44 mm");
                Caliber cal2 = new Caliber("55 mm");

                dao.insertCaliber(cal1);
                dao.insertCaliber(cal2);
                //Weapon word = new Weapon("Hello",0,99);
                //dao.insertWeapon(word);
            });
        }
    };

}