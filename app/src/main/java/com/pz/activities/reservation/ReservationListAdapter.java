package com.pz.activities.reservation;

import com.pz.activities.R;
import com.pz.db.entities.Reservation;
import com.pz.db.entities.Track;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ReservationViewHolder> {



    class ReservationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private final TextView reservationNameView;
        private final TextView reservationSurnameView;
        private final TextView reservationStartTimeView;
        private final TextView reservationTrackIdView;
        private final TextView reservationEndTimeView;

        private final Button reservationCancelButton;

        private ReservationClickListener listener;

        private ReservationViewHolder(View itemView, ReservationClickListener listener)  {
            super(itemView);
            this.listener = listener;
            reservationNameView = itemView.findViewById(R.id.reservation_item_name_text);
            reservationSurnameView = itemView.findViewById(R.id.reservation_item_surname_text);
            reservationStartTimeView = itemView.findViewById(R.id.reservation_item_start_text);
            reservationEndTimeView = itemView.findViewById(R.id.reservation_item_end_text);
            reservationTrackIdView = itemView.findViewById(R.id.reservation_item_track_id_text);
            reservationCancelButton = itemView.findViewById(R.id.reservation_item_cancel_button);
            reservationCancelButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                Reservation clickedReservation = mReservations.get(getAdapterPosition());
                listener.onReservationClick(v.getId(),clickedReservation.reservation_id);
            }
        }
    }
    private ReservationClickListener listener;

    private final LayoutInflater mInflater;
    private List<Reservation> mReservations;
    private List<Track> mTracks;

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
            if(current.is_active) {
                holder.reservationNameView.setText(current.customer_name);
                holder.reservationSurnameView.setText(current.customer_surname);
                holder.reservationStartTimeView.setText(String.valueOf(current.reservation_hour));
                holder.reservationEndTimeView.setText(String.valueOf(current.reservation_hour + current.number_of_Hours));
                for (Track t : mTracks) {
                    if (t.track_id == current.fk_track_id) {
                        holder.reservationTrackIdView.setText(t.track_name);
                        break;
                    }
                }
            }
        }
    }

    void setReservations(List<Reservation> reservations){
        mReservations = reservations;
        notifyDataSetChanged();
    }
    void setTracks(List<Track> tracks){
        mTracks = tracks;
    }

    @Override
    public int getItemCount() {
        if (mReservations != null)
            return mReservations.size();
        else return 0;
    }
}