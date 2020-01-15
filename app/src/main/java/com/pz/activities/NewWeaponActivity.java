package com.pz.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pz.db.ShootingRangeDb;
import com.pz.db.WeaponDAO;
import com.pz.db.entities.Caliber;

import java.util.LinkedList;
import java.util.List;

public class NewWeaponActivity extends AppCompatActivity {

    public static final String WEAPON_NAME_REPLY = "com.example.android.weapon.name.REPLY";
    public static final String CALIBER_ID_REPLY = "com.example.android.weapon.caliber_id.REPLY";
    public static final String PRICE_FOR_SHOOT_REPLY = "com.example.android.weapon.price_for_shoot.REPLY";

    private EditText mEditWeaponName;
    private EditText mEditPriceForShoot;
    private Spinner mCaliberSpinner;

    private List<Caliber> caliberList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_weapon);
        mEditWeaponName = findViewById(R.id.edit_weapon_name);
        mEditPriceForShoot = findViewById(R.id.edit_price_for_shoot);
        createCalibersSpinner();

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditWeaponName.getText())||TextUtils.isEmpty(mCaliberSpinner.getSelectedItem().toString())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String weapon_name = mEditWeaponName.getText().toString();
                    String priceForShoot = mEditPriceForShoot.getText().toString();
                    String caliberPk = String.valueOf(getCaliber(mCaliberSpinner.getSelectedItem().toString()).caliberId);
                    replyIntent.putExtra(WEAPON_NAME_REPLY, weapon_name);
                    replyIntent.putExtra(PRICE_FOR_SHOOT_REPLY, priceForShoot);
                    replyIntent.putExtra(CALIBER_ID_REPLY, caliberPk);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    private Caliber getCaliber(String name){
        for(Caliber cal:caliberList){
            if(cal.caliberName==name){
                return cal;
            }
        }
        return null;
    }
    private void createCalibersSpinner(){
        List<String> caliberStrings = new LinkedList<>();
        caliberList = new LinkedList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                caliberStrings);
        mCaliberSpinner = findViewById(R.id.select_caliber);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCaliberSpinner.setAdapter(adapter);
        ShootingRangeDb.getInstance(this).weaponDAO().getAllCalibers().observe(this, new Observer<List<Caliber>>() {
            @Override
            public void onChanged(@Nullable final List<Caliber> calibers) {
                for(Caliber cal:calibers){
                    caliberStrings.add(cal.caliberName);
                    caliberList.add(cal);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


}