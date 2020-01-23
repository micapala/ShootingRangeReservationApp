package com.pz.activities.weapon;

import com.pz.activities.R;
import com.pz.db.entities.Caliber;
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

public class WeaponListAdapter extends RecyclerView.Adapter<WeaponListAdapter.WeaponViewHolder> {



    class WeaponViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private final ImageView weaponImageView;
        private final TextView weaponNameView;
        private final TextView weaponCaliberView;
        private final TextView weaponPriceView;
        private WeaponClickListener listener;

        private WeaponViewHolder(View itemView,WeaponClickListener listener)  {
            super(itemView);
            this.listener = listener;
            weaponImageView = itemView.findViewById(R.id.weaponImageView);
            weaponNameView = itemView.findViewById(R.id.weaponNameView);
            weaponCaliberView = itemView.findViewById(R.id.weaponCaliberView);
            weaponPriceView = itemView.findViewById(R.id.priceForShootView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onWeaponClick(getAdapterPosition());
            }
        }
    }
    private WeaponClickListener listener;

    private final LayoutInflater mInflater;
    private List<Weapon> mWeapons;
    private List<Caliber> mCalibers;

    public WeaponListAdapter(Context context,WeaponClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public WeaponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_weapon_item, parent, false);
        return new WeaponViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(WeaponViewHolder holder, int position) {
        if (mWeapons != null&&mCalibers!=null) {
            Weapon current = mWeapons.get(position);
            holder.weaponNameView.setText(current.weaponModel);
            holder.weaponPriceView.setText(String.valueOf(current.priceForShoot));
            holder.weaponImageView.setImageBitmap( BitmapFactory.decodeByteArray(current.weapon_image, 0, current.weapon_image.length));
            for (Caliber cal:mCalibers){
                if(cal.caliberId==current.fk_caliber_id) holder.weaponCaliberView.setText(cal.caliberName);
            }
        } else {
            holder.weaponNameView.setText("No Weapon");
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

    @Override
    public int getItemCount() {
        if (mWeapons != null)
            return mWeapons.size();
        else return 0;
    }
}