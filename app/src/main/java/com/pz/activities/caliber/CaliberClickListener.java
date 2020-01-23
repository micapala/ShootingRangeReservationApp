package com.pz.activities.caliber;

import com.pz.db.entities.Caliber;

import java.util.List;

public interface CaliberClickListener {
    void onCaliberClick(int viewId, int position, List<Caliber> caliberList);
}
