package com.pz.activities.reservation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pz.activities.R;
import com.pz.activities.weapon.ListWeaponsActivity;
import com.pz.activities.weapon.WeaponEditActivity;

public class ListReservationsActivity extends AppCompatActivity implements ReservationClickListener{

    public static final int NEW_RESERVATION_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservations);

        FloatingActionButton fab = findViewById(R.id.add_reservation_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListReservationsActivity.this, ReservationEditActivity.class);
                startActivityForResult(intent, NEW_RESERVATION_ACTIVITY_REQUEST_CODE);
            }
        });

    }
    @Override
    public void onReservationClick(int position) {

    }
}
