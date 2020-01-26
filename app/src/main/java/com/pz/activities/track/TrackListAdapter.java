package com.pz.activities.track;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pz.activities.R;
import com.pz.db.entities.Track;

import java.util.List;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.TrackViewHolder> {
    private TrackClickListener listener;
    private final LayoutInflater mInflater;
    private List<Track> mTracks;


    class TrackViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private final TextView trackNameTextView;
        private final TextView trackLengthTextView;
        private final EditText trackNameEditText;
        private final EditText trackLengthEditText;

       // private final Button trackDeleteButton;
        private final Button trackEditButton;

        private TrackClickListener listener;

        private TrackViewHolder(View itemView, TrackClickListener listener)  {
            super(itemView);
            this.listener = listener;

            trackNameTextView = itemView.findViewById(R.id.track_item_name_text_view);
            trackLengthTextView = itemView.findViewById(R.id.track_item_length_text_view);
            trackNameEditText = itemView.findViewById(R.id.track_item_name_edit_text);
            trackLengthEditText = itemView.findViewById(R.id.track_item_length_edit_text);
            //trackDeleteButton = itemView.findViewById(R.id.track_item_delete_button);
            trackEditButton = itemView.findViewById(R.id.track_item_edit_button);
            //trackDeleteButton.setOnClickListener(this);
            trackEditButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if(listener!=null){
                int id = v.getId();
                switch(id){
                    case R.id.track_item_edit_button:
                        int visibility = trackNameTextView.getVisibility();
                        if(visibility==View.VISIBLE){
                            trackNameTextView.setVisibility(View.INVISIBLE);
                            trackLengthTextView.setVisibility(View.INVISIBLE);
                            trackNameEditText.setVisibility(View.VISIBLE);
                            trackLengthEditText.setVisibility(View.VISIBLE);
                            trackEditButton.setText("Zapisz");
                            trackNameEditText.setText(trackNameTextView.getText().toString());
                            trackLengthEditText.setText(trackLengthTextView.getText().toString());
                        }
                        else{
                            trackNameEditText.setVisibility(View.GONE);
                            trackLengthEditText.setVisibility(View.GONE);
                            trackLengthTextView.setVisibility(View.VISIBLE);
                            trackNameTextView.setVisibility(View.VISIBLE);
                            trackEditButton.setText("Edytuj");
                            Track track = mTracks.get(getAdapterPosition());
                            track.track_name = trackNameEditText.getText().toString();
                            track.track_length = Integer.valueOf(trackLengthEditText.getText().toString());
                            listener.onTrackClick(id,getAdapterPosition(),track);
                        }
                }
            }

        }
    }


    protected TrackListAdapter(Context context, TrackClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_track_item, parent, false);
        return new TrackViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        if (mTracks!=null) {
            Track current = mTracks.get(position);
            holder.trackNameTextView.setText(current.track_name);
            holder.trackLengthTextView.setText(String.valueOf(current.track_length));
        }
    }

    void setTracks(List<Track> tracks){
        mTracks = tracks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTracks != null)
            return mTracks.size();
        else return 0;
    }
}
