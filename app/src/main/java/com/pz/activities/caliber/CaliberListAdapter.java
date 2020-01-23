package com.pz.activities.caliber;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pz.activities.R;
import com.pz.db.entities.Caliber;

import java.util.List;

public class CaliberListAdapter extends RecyclerView.Adapter<CaliberListAdapter.WeaponViewHolder> {




    class WeaponViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private final TextView weaponCaliberView;
        private CaliberClickListener listener;

        private WeaponViewHolder(View itemView,CaliberClickListener listener)  {
            super(itemView);
            this.listener = listener;

            weaponCaliberView = itemView.findViewById(R.id.caliberName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onCaliberClick(getAdapterPosition());
            }
        }
    }
    private CaliberClickListener listener;
    private final LayoutInflater mInflater;
    private List<Caliber> mCalibers;

    protected CaliberListAdapter(Context context, CaliberClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public WeaponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_caliber_item, parent, false);
        return new WeaponViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull WeaponViewHolder holder, int position) {
        if (mCalibers!=null) {
            Caliber current = mCalibers.get(position);
            holder.weaponCaliberView.setText(current.caliberName);
        } else {
            holder.weaponCaliberView.setText("No Caliber");
        }
    }

    void setCalibers(List<Caliber> calibers){
        mCalibers = calibers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCalibers != null)
            return mCalibers.size();
        else return 0;
    }
}
