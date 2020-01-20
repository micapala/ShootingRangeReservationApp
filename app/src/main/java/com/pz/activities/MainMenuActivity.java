package com.pz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.pz.activities.caliber.ListCalibersActivity;
import com.pz.activities.weapon.ListWeaponsActivity;

public class MainMenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button weapons_menu_button = findViewById(R.id.weapons_menu_button);
        Button reservations_menu_button = findViewById(R.id.reservations_menu_button);
        Button calibers_menu_button = findViewById(R.id.calibers_menu_button);

        weapons_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, ListWeaponsActivity.class);
                startActivity(intent);
            }
        });
        calibers_menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ListCalibersActivity.class);
                startActivity(intent);
            }
        });

    }

}
