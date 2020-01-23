package com.pz.activities.caliber;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pz.activities.R;
import com.pz.db.entities.Caliber;

import java.util.List;

public class CaliberListAdapter extends RecyclerView.Adapter<CaliberListAdapter.CaliberViewHolder> {

    private CaliberClickListener listener;
    private final LayoutInflater mInflater;
    private List<Caliber> mCalibers;


    class CaliberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

        private final TextView caliberNameView;
        private final Button caliberDeleteView;
        private final Button caliberEditView;

        private CaliberClickListener listener;

        private CaliberViewHolder(View itemView, CaliberClickListener listener)  {
            super(itemView);
            this.listener = listener;

            caliberNameView = itemView.findViewById(R.id.caliberName);
            caliberDeleteView = itemView.findViewById(R.id.caliber_delete_button);
            caliberEditView = itemView.findViewById(R.id.caliber_edit_button);
            caliberDeleteView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                if(v.getId()==R.id.caliber_delete_button)
                    listener.onCaliberClick(v.getId(),getAdapterPosition(),mCalibers);
                else if(v.getId()==R.id.caliber_edit_button) {
                    caliberNameView.setInputType(InputType.TYPE_CLASS_TEXT);
                    caliberNameView.setFocusable(true);
                    caliberNameView.setFocusableInTouchMode(true);
                    caliberNameView.setClickable(true);

                }
                else
                    listener.onCaliberClick(itemView.getId(),getAdapterPosition(),mCalibers);
            }
        }
    }


    protected CaliberListAdapter(Context context, CaliberClickListener listener){
        this.mInflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public CaliberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_caliber_item, parent, false);
        return new CaliberViewHolder(itemView,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CaliberViewHolder holder, int position) {
        if (mCalibers!=null) {
            Caliber current = mCalibers.get(position);
            holder.caliberNameView.setText(current.caliberName);
        } else {
            holder.caliberNameView.setText("No Caliber");
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
