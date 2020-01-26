package com.pz.activities.reservation;

import com.pz.activities.R;
import com.pz.db.entities.Caliber;
import com.pz.db.entities.Reservation;
import com.pz.db.entities.Weapon;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ReservationViewHolder> {



    class ReservationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private final TextView reservationNameView;
        private final TextView reservationSurnameView;
        private final TextView reservationStartTimeView;

        private final TextView reservationEndTimeView;
       // private final TextView reservationNameView;
        //private final TextView reservationNameView;
        private ReservationClickListener listener;

        private ReservationViewHolder(View itemView, ReservationClickListener listener)  {
            super(itemView);
            this.listener = listener;
            reservationNameView = itemView.findViewById(R.id.reservation_name_field);
            reservationSurnameView = itemView.findViewById(R.id.reservation_surname_field);
            reservationStartTimeView = itemView.findViewById(R.id.reservation_list_start_time);
            reservationEndTimeView = itemView.findViewById(R.id.reservation_list_end_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onReservationClick(getAdapterPosition());
            }
        }
    }
    private ReservationClickListener listener;

    private final LayoutInflater mInflater;
    private List<Reservation> mReservations;

    public ReservationListAdapter(Context context,ReservationClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public ReservationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_reservation_item, parent, false);
        return new ReservationViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(ReservationViewHolder holder, int position) {
        if (mReservations != null) {
            Reservation current = mReservations.get(position);
            holder.reservationNameView.setText(current.customer_name);
            holder.reservationSurnameView.setText(current.customer_surname);
            holder.reservationStartTimeView.setText(String.valueOf(current.reservation_hour));
            holder.reservationEndTimeView.setText(String.valueOf(current.reservation_hour+current.number_of_Hours));


        }
    }

    void setReservations(List<Reservation> reservations){
        mReservations = reservations;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mReservations != null)
            return mReservations.size();
        else return 0;
    }
}