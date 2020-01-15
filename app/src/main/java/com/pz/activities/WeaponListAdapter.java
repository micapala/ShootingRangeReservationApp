package com.pz.activities;

import com.pz.db.ShootingRangeDb;
import com.pz.db.entities.Caliber;
import com.pz.db.entities.Weapon;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeaponListAdapter extends RecyclerView.Adapter<WeaponListAdapter.WeaponViewHolder> {

    class WeaponViewHolder extends RecyclerView.ViewHolder {
        private final TextView weaponNameView;
        private final TextView weaponCaliberView;
        private final TextView weaponPriceView;

        private WeaponViewHolder(View itemView) {
            super(itemView);
            weaponNameView = itemView.findViewById(R.id.weaponNameView);
            weaponCaliberView = itemView.findViewById(R.id.weaponCaliberView);
            weaponPriceView = itemView.findViewById(R.id.priceForShootView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Weapon> mWeapons; // Cached copy of words
    private List<Caliber> mCalibers;

    WeaponListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public WeaponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new WeaponViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WeaponViewHolder holder, int position) {
        if (mWeapons != null&&mCalibers!=null) {
            Weapon current = mWeapons.get(position);
            holder.weaponNameView.setText(current.weaponModel);
            holder.weaponPriceView.setText(String.valueOf(current.priceForShoot));
            for (Caliber cal:mCalibers){
                if(cal.caliberId==current.fk_caliber_id) holder.weaponCaliberView.setText(cal.caliberName);
            }
        } else {
            // Covers the case of data not being ready yet.
            holder.weaponNameView.setText("No Word");
        }
    }

    void setWeapons(List<Weapon> weapons){
        mWeapons = weapons;
        notifyDataSetChanged();
    }
    void setCalibers(List<Caliber> calibers){
        mCalibers = calibers;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWeapons != null)
            return mWeapons.size();
        else return 0;
    }
}