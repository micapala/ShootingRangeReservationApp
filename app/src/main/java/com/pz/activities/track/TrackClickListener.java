package com.pz.activities.track;

import com.pz.db.entities.Track;


public interface TrackClickListener {

    void onTrackClick(int viewId, int position,Track track);

}
