package com.pz.activities.caliber;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pz.activities.R;
import com.pz.ShootingRangeViewModel;
import com.pz.activities.caliber.CaliberClickListener;
import com.pz.activities.weapon.WeaponEditActivity;
import com.pz.db.entities.Caliber;

import java.util.ArrayList;
import java.util.List;



public class ListCalibersActivity extends AppCompatActivity implements CaliberClickListener {

    public static ShootingRangeViewModel mCaliberViewModel;
    public static final int NEW_WEAPON_ACTIVITY_REQUEST_CODE = 1;

    private CaliberListAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calibers);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new CaliberListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCaliberViewModel = new ViewModelProvider(this).get(ShootingRangeViewModel.class);


        mCaliberViewModel.getAllCalibers().observe(this, new Observer<List<Caliber>>() {
            @Override
            public void onChanged(@Nullable final List<Caliber> calibers) {
                // Update the cached copy of the words in the adapter.
                adapter.setCalibers(calibers);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);

            /*
            tutaj bedzie edycja kalibru

            fab.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                //tutaj trzeba zmienic jeszcze ten weaponeditactivity
                Intent intent = new Intent(ListCalibersActivity.this, WeaponEditActivity.class);
                startActivityForResult(intent, NEW_WEAPON_ACTIVITY_REQUEST_CODE);
            }


        });
        */

    }





    @Override
    public void onCaliberClick(int position) {

    }
}
