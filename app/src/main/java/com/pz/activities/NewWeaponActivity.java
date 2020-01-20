package com.pz.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;

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

import com.pz.db.ShootingRangeDb;
import com.pz.db.entities.Caliber;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class NewWeaponActivity extends AppCompatActivity {

    public static final String WEAPON_NAME_REPLY = "com.example.android.weapon.name.REPLY";
    public static final String CALIBER_ID_REPLY = "com.example.android.weapon.caliber_id.REPLY";
    public static final String PRICE_FOR_SHOOT_REPLY = "com.example.android.weapon.price_for_shoot.REPLY";
    public static final String WEAPON_IMAGE_REPLY = "com.example.android.weapon.weapon_image.REPLY";
    private static int RESULT_LOAD_IMAGE = 1;

    private EditText mEditWeaponName;
    private EditText mEditPriceForShoot;
    private Spinner mCaliberSpinner;

    private List<Caliber> caliberList;

    Intent replyIntent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_weapon);
        mEditWeaponName = findViewById(R.id.edit_weapon_name);
        mEditPriceForShoot = findViewById(R.id.edit_price_for_shoot);
        createCalibersSpinner();

        final Button button = findViewById(R.id.button_save);


        Button fab = findViewById(R.id.add_image);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditWeaponName.getText())||
                        TextUtils.isEmpty(mCaliberSpinner.getSelectedItem().toString())||
                        TextUtils.isEmpty((mEditPriceForShoot.getText()))) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView image_view = findViewById(R.id.imageView);
                image_view.setImageBitmap(selectedImage);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                selectedImage.compress(Bitmap.CompressFormat.PNG, 100, bos);
                byte[] imageByteArray = bos.toByteArray();
                replyIntent.putExtra(WEAPON_IMAGE_REPLY, imageByteArray);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(NewWeaponActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
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