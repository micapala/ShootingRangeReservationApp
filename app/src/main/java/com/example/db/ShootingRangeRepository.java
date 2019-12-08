package com.example.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ShootingRangeRepository {
    private WeaponDAO mWeaponDAO;
    private LiveData<List<Weapon>> mAllWeapons;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public ShootingRangeRepository(Application application) {
        ShootingRangeDb db = ShootingRangeDb.getDatabase(application);
        mWeaponDAO = db.weaponDAO();
        mAllWeapons = mWeaponDAO.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Weapon>> getAllWords() {
        return mAllWeapons;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insert(Weapon word) {
        ShootingRangeDb.databaseWriteExecutor.execute(() -> {
            mWeaponDAO.insert(word);
        });
    }
}
