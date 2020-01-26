package com.pz.activities.reservation;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.pz.ShootingRangeViewModel;
import com.pz.activities.R;
import com.pz.db.entities.Reservation;
import com.pz.db.entities.Track;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationEditActivity extends AppCompatActivity implements View.OnClickListener
{
    private CalendarView mCalendarView;
    private EditText customerSurname;
    private EditText customerName;
    private Spinner reservationLength;
    private Spinner hourOfReservation;
    private ShootingRangeViewModel mViewModel;
    Map<Integer,List<Track>> avilableTracksAtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reservation);
        Button saveReservation = findViewById(R.id.reservation_save_button);

        mViewModel = new ViewModelProvider(this).get(ShootingRangeViewModel.class);

        customerSurname = findViewById(R.id.reservation_surname);
        customerName = findViewById(R.id.reservation_name);
        reservationLength = findViewById(R.id.reservationLength);
        hourOfReservation = findViewById(R.id.reservationHour);
        mCalendarView = findViewById(R.id.reservation_start);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                Calendar c = new GregorianCalendar();
                c.set(year,month,dayOfMonth);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                mCalendarView.setDate(c.getTimeInMillis());
                refreshAvilableHours();
            }
        });

        reservationLength.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                refreshAvilableHours();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                refreshAvilableHours();
            }
        });

        mViewModel.getAllActiveReservationsLive().observe(this, new Observer<List<Reservation>>() {
            @Override
            public void onChanged(@Nullable final List<Reservation> reservations) {
                refreshAvilableHours();
            }
        });

        saveReservation.setOnClickListener(this);

        populateReservationLengthSpinner();
        refreshAvilableHours();
    }

    private void populateReservationLengthSpinner(){
        List<String> hoursList = new ArrayList<>();
        for(int i =1;i<=(ShootingRangeViewModel.closeHour-ShootingRangeViewModel.openingHour);i++){
            hoursList.add(String.valueOf(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, hoursList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reservationLength.setAdapter(dataAdapter);

    }

    private void refreshAvilableHours(){
        List<String> hoursList = new ArrayList<>();
        List<Reservation> allReservations;
        List<Track> allTracks = mViewModel.getAllTracks();
        List<Track> allTracksCopy = new ArrayList<>(allTracks);
        avilableTracksAtTime = new HashMap<>();
        int numberOfHours = Integer.parseInt(reservationLength.getSelectedItem().toString());

        List<Reservation> reservationsOfDay = new ArrayList<>();
        allReservations = mViewModel.getAllActiveReservations();
        if(allReservations!=null){
            for(Reservation r:allReservations){
                if(r.reservation_date==roundDateToMidnight(mCalendarView.getDate())) reservationsOfDay.add(r);
            }
        }

        for(int hour =ShootingRangeViewModel.openingHour;hour<ShootingRangeViewModel.closeHour;hour++){
            boolean isValidHour = true;
            int invalidIter = 0;
                for (Reservation r : reservationsOfDay) {
                    if (hour < r.reservation_hour + r.number_of_Hours && hour + numberOfHours > r.reservation_hour){
                        invalidIter++;
                        for(Track t:allTracksCopy){
                            if(t.track_id==r.fk_track_id) {
                                allTracksCopy.remove(t);
                                break;
                            }
                        }
                        if(invalidIter==allTracks.size()){
                            isValidHour = false;
                            break;
                        }
                    }
            }
            if(isValidHour&&((hour+numberOfHours)<=ShootingRangeViewModel.closeHour)) {
                hoursList.add(String.valueOf(hour));
                avilableTracksAtTime.put(hour, allTracksCopy);
            }
            allTracksCopy = new ArrayList<>(allTracks);
        }

        if(hoursList.size()==0){
            hoursList.add("Brak dostępnych godzin.");
            hourOfReservation.setClickable(false);
           // hourOfReservation.setVisibility(View.INVISIBLE);
        }else{
            hourOfReservation.setClickable(true);
           // hourOfReservation.setVisibility(View.VISIBLE);
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, hoursList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hourOfReservation.setAdapter(dataAdapter);
        dataAdapter.notifyDataSetChanged();
    }


    private long roundDateToMidnight(long date){
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.reservation_save_button:
                if(!hourOfReservation.getSelectedItem().toString().equals("Brak dostępnych godzin.")) {
                    String customer_name = customerName.getText().toString();
                    String customer_surname = customerSurname.getText().toString();
                    long reservation_date = roundDateToMidnight(mCalendarView.getDate());
                    int reservationHour = Integer.parseInt(hourOfReservation.getSelectedItem().toString());
                    int reservationLength_t = Integer.parseInt(reservationLength.getSelectedItem().toString());
                    Reservation new_reservation = new Reservation(customer_name, customer_surname, reservation_date, reservationHour, reservationLength_t,avilableTracksAtTime.get(reservationHour).get(0).track_id);
                    mViewModel.insertReservation(new_reservation);
                    finish();
                }else{
                    Toast.makeText(this, "Nie wybrano prawidłowej godziny.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.reservationLength:
                refreshAvilableHours();
                break;
        }

    }
}
