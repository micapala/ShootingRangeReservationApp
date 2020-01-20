package com.pz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pz.db.entities.Caliber;
import com.pz.db.entities.Weapon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;




public class MainActivity extends AppCompatActivity implements WeaponClickListener {
    public static ShootingRangeViewModel mWeaponViewModel;
    public static final int NEW_WEAPON_ACTIVITY_REQUEST_CODE = 1;
    public static final int WEAPON_INFO_ACTIVITY_REQUEST_CODE = 2;

    private WeaponListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new WeaponListAdapter(this,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mWeaponViewModel = new ViewModelProvider(this).get(ShootingRangeViewModel.class);

        mWeaponViewModel.getAllWeapons().observe(this, new Observer<List<Weapon>>() {
            @Override
            public void onChanged(@Nullable final List<Weapon> weapons) {
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
                Intent intent = new Intent(MainActivity.this, WeaponEditActivity.class);
                startActivityForResult(intent, NEW_WEAPON_ACTIVITY_REQUEST_CODE);
            }
        });



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_WEAPON_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String weapon_name = data.getStringExtra(WeaponEditActivity.WEAPON_NAME_REPLY);
            String caliber_id = data.getStringExtra(WeaponEditActivity.CALIBER_ID_REPLY);
            String price_for_shoot = data.getStringExtra(WeaponEditActivity.PRICE_FOR_SHOOT_REPLY);
            byte[] weapon_image = data.getByteArrayExtra(WeaponEditActivity.WEAPON_IMAGE_REPLY);


            if(data.getStringExtra(WeaponEditActivity.WEAPON_NEW_REPLY)!=null){
                Weapon weapon = new Weapon(weapon_image,weapon_name,Integer.parseInt(caliber_id),Integer.parseInt(price_for_shoot));
                mWeaponViewModel.insertWeapon(weapon);
            }
            else{
                int weapon_id = data.getIntExtra(WeaponEditActivity.WEAPON_EDIT_REPLY,-1);
                mWeaponViewModel.updateWeapon(weapon_id,weapon_image,weapon_name,Integer.valueOf(caliber_id),Integer.valueOf(price_for_shoot));
            }
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWeaponClick(int position) {
        List<Weapon> weapons = new ArrayList<>();
        mWeaponViewModel.getAllWeapons().observe(this, new Observer<List<Weapon>>() {
            @Override
            public void onChanged(@Nullable final List<Weapon> weaponsDb) {
                for(Weapon w:weaponsDb)
                    weapons.add(w);
            }
        });
        Weapon weapon = weapons.get(position);
        Intent intent = new Intent(MainActivity.this, WeaponEditActivity.class);
        intent.putExtra("weapon_id", weapon.weaponPK);
        startActivityForResult(intent, NEW_WEAPON_ACTIVITY_REQUEST_CODE);
    }
}










