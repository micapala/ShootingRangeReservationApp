package com.pz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pz.db.entities.Caliber;
import com.pz.db.entities.Weapon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;




public class MainActivity extends AppCompatActivity  {
    private ShootingRangeViewModel mWeaponViewModel;
    public static final int NEW_WEAPON_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final WeaponListAdapter adapter = new WeaponListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get a new or existing ViewModel from the ViewModelProvider.
        mWeaponViewModel = new ViewModelProvider(this).get(ShootingRangeViewModel.class);


        // Add an observer on the LiveData returned by getAlphabetizedWeapons.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWeaponViewModel.getAllWeapons().observe(this, new Observer<List<Weapon>>() {
            @Override
            public void onChanged(@Nullable final List<Weapon> weapons) {
                // Update the cached copy of the words in the adapter.
                adapter.setWeapons(weapons);
            }
        });
        mWeaponViewModel.getAllCalibers().observe(this, new Observer<List<Caliber>>() {
            @Override
            public void onChanged(@Nullable final List<Caliber> calibers) {
                // Update the cached copy of the words in the adapter.
                adapter.setCalibers(calibers);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWeaponActivity.class);
                startActivityForResult(intent, NEW_WEAPON_ACTIVITY_REQUEST_CODE);
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WEAPON_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String weapon_name = data.getStringExtra(NewWeaponActivity.WEAPON_NAME_REPLY);
            String caliber_id = data.getStringExtra(NewWeaponActivity.CALIBER_ID_REPLY);
            String price_for_shoot = data.getStringExtra(NewWeaponActivity.PRICE_FOR_SHOOT_REPLY);
            Weapon word = new Weapon(weapon_name,Integer.parseInt(caliber_id),Integer.parseInt(price_for_shoot));
            mWeaponViewModel.insertWeapon(word);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}

