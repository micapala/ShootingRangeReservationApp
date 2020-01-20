package com.pz.activities.weapon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.pz.activities.R;
import com.pz.ShootingRangeViewModel;
import com.pz.db.entities.Caliber;
import com.pz.db.entities.Weapon;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class WeaponEditActivity extends AppCompatActivity {

    public static final String WEAPON_NAME_REPLY = "com.example.android.weapon.name.REPLY";
    public static final String CALIBER_ID_REPLY = "com.example.android.weapon.caliber_id.REPLY";
    public static final String PRICE_FOR_SHOOT_REPLY = "com.example.android.weapon.price_for_shoot.REPLY";
    public static final String WEAPON_IMAGE_REPLY = "com.example.android.weapon.weapon_image.REPLY";
    public static final String WEAPON_ID_REPLY = "com.example.android.weapon.weapon_edit.REPLY";

    public static final int RESULT_NEW_WEAPON = 1;
    public static final int RESULT_EDIT_WEAPON = 2;
    public static final int RESULT_DELETE_WEAPON = 3;
    private static int RESULT_LOAD_IMAGE = 1;

    private EditText mEditWeaponName;
    private EditText mEditPriceForShoot;
    private Spinner mCaliberSpinner;
    private ImageView mWeaponImage;

    private List<Caliber> caliberList;

    private List<Weapon> weaponList;

    private Intent replyIntent = new Intent();


    private ShootingRangeViewModel viewModel;

    private boolean isEditActivity = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent callerIntent = this.getIntent();
        setContentView(R.layout.activity_new_weapon);
        final Button save_button = findViewById(R.id.save_button);

        Button add_image_button = findViewById(R.id.add_image_button);
        Button remove_weapon_button = findViewById(R.id.delete_weapon_button);

        viewModel = new ViewModelProvider(this).get(ShootingRangeViewModel.class);

        mEditWeaponName = findViewById(R.id.edit_weapon_name);
        mEditPriceForShoot = findViewById(R.id.edit_price_for_shoot);
        mWeaponImage = findViewById(R.id.imageView);

        createCalibersSpinner();

        if(callerIntent!=null&&callerIntent.hasExtra("weapon_id")){
            getWeaponAndSetFields(callerIntent.getIntExtra("weapon_id",-1));
            isEditActivity = true;
            remove_weapon_button.setVisibility(View.VISIBLE);
        }

        remove_weapon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_DELETE_WEAPON, replyIntent);
                replyIntent.putExtra(WEAPON_ID_REPLY,callerIntent.getIntExtra("weapon_id",-1));
                finish();
            }
        });



        add_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });
        save_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditWeaponName.getText())||
                        TextUtils.isEmpty(mCaliberSpinner.getSelectedItem().toString())||
                        TextUtils.isEmpty((mEditPriceForShoot.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    if(isEditActivity) {
                        replyIntent.putExtra(WEAPON_ID_REPLY,callerIntent.getIntExtra("weapon_id",-1));
                        setResult(RESULT_EDIT_WEAPON, replyIntent);
                    }
                    else{
                        setResult(RESULT_NEW_WEAPON, replyIntent);
                    }
                    String weapon_name = mEditWeaponName.getText().toString();
                    String priceForShoot = mEditPriceForShoot.getText().toString();
                    String caliberPk = String.valueOf(getCaliber(mCaliberSpinner.getSelectedItem().toString()).caliberId);
                    replyIntent.putExtra(WEAPON_NAME_REPLY, weapon_name);
                    replyIntent.putExtra(PRICE_FOR_SHOOT_REPLY, priceForShoot);
                    replyIntent.putExtra(CALIBER_ID_REPLY, caliberPk);

                }
                finish();
            }
        });
    }

    private void setFields(Weapon weapon){
        mEditWeaponName.setText(weapon.weaponModel);
        mEditPriceForShoot.setText(String.valueOf(weapon.priceForShoot));
        mWeaponImage.setImageBitmap(BitmapFactory.decodeByteArray(weapon.weapon_image, 0, weapon.weapon_image.length));
        replyIntent.putExtra(WEAPON_IMAGE_REPLY, weapon.weapon_image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                mWeaponImage.setImageBitmap(selectedImage);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] imageByteArray = bos.toByteArray();
                replyIntent.putExtra(WEAPON_IMAGE_REPLY, imageByteArray);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(WeaponEditActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }


    }

    private Caliber getCaliber(String name){
        for(Caliber cal:caliberList){
            if(cal.caliberName==name){
                return cal;
            }
        }
        return null;
    }
    private void getWeaponAndSetFields(int weapon_id){
        viewModel.getAllWeapons().observe(this, new Observer<List<Weapon>>() {
            @Override
            public void onChanged(@Nullable final List<Weapon> weaponsDb) {
                for(Weapon weapon:weaponsDb){
                    if(weapon.weaponPK == weapon_id) setFields(weapon);
                }
            }
        });
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
        viewModel.getAllCalibers().observe(this, new Observer<List<Caliber>>() {
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