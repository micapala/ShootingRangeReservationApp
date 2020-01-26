package com.pz.activities.caliber;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pz.activities.R;
import com.pz.ShootingRangeViewModel;
import com.pz.db.entities.Caliber;
import com.pz.db.entities.Weapon;

import java.util.ArrayList;
import java.util.List;



public class ListCalibersActivity extends AppCompatActivity implements CaliberClickListener {

    public static ShootingRangeViewModel mViewModel;
    public static final int NEW_WEAPON_ACTIVITY_REQUEST_CODE = 1;

    private CaliberListAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_calibers);

        RecyclerView recyclerView = findViewById(R.id.calibers_recyclerview);
        adapter = new CaliberListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel = new ViewModelProvider(this).get(ShootingRangeViewModel.class);


        mViewModel.getAllCalibersLive().observe(this, new Observer<List<Caliber>>() {
            @Override
            public void onChanged(@Nullable final List<Caliber> calibers) {
                adapter.setCalibers(calibers);
            }
        });

        FloatingActionButton fab = findViewById(R.id.add_caliber_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog(ListCalibersActivity.this);
            }
        });
    }


    @Override
    public void onCaliberClick(int viewId,int position,List<Caliber> caliberList) {

        if(viewId == R.id.caliber_delete_button){
            Caliber clickedCaliber = caliberList.get(position);
            List<Weapon> weaponsList = mViewModel.getAllWeapons();

            Log.d("Masno fest",String.valueOf(weaponsList.size()));
            boolean isCaliberUsed = false;
            if(weaponsList.size()!=0){
                for(Weapon w:weaponsList){
                    if(w.fk_caliber_id==clickedCaliber.caliberId){
                        Toast.makeText(this, "Kaliber jest używany przez jedną z broni.", Toast.LENGTH_LONG).show();
                        isCaliberUsed = true;
                        break;
                    }
                }
            }
            if(!isCaliberUsed){
                mViewModel.deleteCaliber(clickedCaliber.caliberId);
                Toast.makeText(this, "Kaliber usunięty.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Dodaj nowy kaliber")
                .setView(taskEditText)
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Caliber c = new Caliber(taskEditText.getText().toString());
                        mViewModel.insertCaliber(c);

                    }
                })
                .setNegativeButton("Anuluj", null)
                .create();
        dialog.show();
    }
}
