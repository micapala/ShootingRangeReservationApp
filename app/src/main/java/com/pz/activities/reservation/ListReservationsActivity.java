package com.pz.activities.reservation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pz.ShootingRangeViewModel;
import com.pz.activities.R;
import com.pz.db.entities.Reservation;
import com.pz.db.entities.Track;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ListReservationsActivity extends AppCompatActivity implements ReservationClickListener{

    public static final int NEW_RESERVATION_ACTIVITY_REQUEST_CODE = 1;
    public static ShootingRangeViewModel mViewModel;
    public static Date selectedReservationDate;

    private ReservationListAdapter adapter;

    private static TextView selectedDateView;
    private Button setDateButon;

    private DatePickerDialog dpd;
    private Calendar calendar;

    Observer<List<Reservation>> reservationEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservations);
        selectedDateView = findViewById(R.id.reservations_list_selected_date);
        setDateButon = findViewById(R.id.reservations_list_set_date_button);

        mViewModel = new ViewModelProvider(this).get(ShootingRangeViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.reservations_recyclerview);

        setDateOnTop(Calendar.getInstance());

        adapter = new ReservationListAdapter(this, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel.getAllTracksLive().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(List<Track> tracks) {
                adapter.setTracks(tracks);
            }
        });

        setDateButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                dpd = new DatePickerDialog(ListReservationsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar c = new GregorianCalendar();
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        setDateOnTop(c);
                    }
                },year,month,day);

                dpd.show();
            }
        });
        updateObserver();

        FloatingActionButton fab = findViewById(R.id.reservation_list_add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListReservationsActivity.this, ReservationEditActivity.class);
                startActivityForResult(intent, NEW_RESERVATION_ACTIVITY_REQUEST_CODE);
            }
        });

    }

    private void updateObserver(){
        if(reservationEntries!=null)
            mViewModel.getActiveReservationsFromDay(selectedReservationDate.getTime()).removeObserver(reservationEntries);
        reservationEntries = new Observer<List<Reservation>>() {
            @Override
            public void onChanged(@Nullable List<Reservation> reservations) {
                adapter.setReservations(reservations);
            }
        };
        mViewModel.getActiveReservationsFromDay(selectedReservationDate.getTime()).observeForever(reservationEntries);

    }
    @Override
    public void onReservationClick(int viewId,int reservation_id) {
        if(viewId == R.id.reservation_item_cancel_button){
            mViewModel.cancelReservation(reservation_id);
        }

    }

    private void setDateOnTop(Calendar c){
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        c.set(Calendar.MILLISECOND,0);
        selectedReservationDate = c.getTime();
        SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy");
        selectedDateView.setText( formatter.format(selectedReservationDate.getTime()));
        updateObserver();
    }

}
