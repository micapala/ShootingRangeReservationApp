package com.example.projektpz;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.db.ShootingRangeRepository;
import com.example.db.Weapon;

import java.util.List;

public class ShootingRangeViewModel extends AndroidViewModel {
    private ShootingRangeRepository mRepository;

    private LiveData<List<Weapon>> mAllWeapons;

    public ShootingRangeViewModel(Application application){
        super(application);
        mRepository = new ShootingRangeRepository(application);
        mAllWeapons = mRepository.getAllWords();
    }
    LiveData<List<Weapon>> getAllWords() { return mAllWeapons; }

    public void insert(Weapon word) { mRepository.insert(word); }
}
