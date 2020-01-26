package com.pz.activities.track;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pz.ShootingRangeViewModel;
import com.pz.activities.R;
import com.pz.db.entities.Track;

import java.util.List;

public class ListTracksActivity extends AppCompatActivity implements TrackClickListener {

    private TrackListAdapter adapter;

    public static ShootingRangeViewModel mViewModel;
    public static final int NEW_WEAPON_ACTIVITY_REQUEST_CODE = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tracks);

        RecyclerView recyclerView = findViewById(R.id.tracks_recyclerview);
        adapter = new TrackListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel = new ViewModelProvider(this).get(ShootingRangeViewModel.class);


        mViewModel.getAllTracksLive().observe(this, new Observer<List<Track>>() {
            @Override
            public void onChanged(@Nullable final List<Track> tracks) {
                adapter.setTracks(tracks);
            }
        });

        FloatingActionButton fab = findViewById(R.id.add_track_button);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTrackDialog(ListTracksActivity.this);
            }
        });
    }
    private void showAddTrackDialog(Context c) {
        LinearLayout layout = new LinearLayout(c);
        layout.setOrientation(LinearLayout.VERTICAL);
        EditText trackNameEditText = new EditText(c);
        trackNameEditText.setHint("Nazwa toru");
        EditText trackLenghEditText = new EditText(c);
        trackLenghEditText.setHint("Długość toru");
        layout.addView(trackNameEditText);
        layout.addView(trackLenghEditText);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Dodaj nowy tor")
                .setView(layout)
                .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Track t = new Track(trackNameEditText.getText().toString(),Integer.valueOf(trackLenghEditText.getText().toString()));
                        mViewModel.insertTrack(t);
                    }
                })
                .setNegativeButton("Anuluj", null)
                .create();
        dialog.show();
    }

    @Override
    public void onTrackClick(int viewId, int position,Track track) {
        if(viewId == R.id.track_item_edit_button){
            mViewModel.updateTrack(track.track_id,track.track_name,track.track_length);
            Toast.makeText(ListTracksActivity.this, "Zaktualiizowano tory", Toast.LENGTH_LONG).show();
        }

    }
}
