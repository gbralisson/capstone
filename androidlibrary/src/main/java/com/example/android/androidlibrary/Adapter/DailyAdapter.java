package com.example.android.androidlibrary.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.androidlibrary.Model.Daily;
import com.example.android.androidlibrary.R;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyAdapterViewHolder>{

    private DailyAdapterOnClickListener dailyAdapterOnClickListener;
    private Daily[] dailies;

    public DailyAdapter(DailyAdapterOnClickListener dailyAdapterOnClickListener){
        this.dailyAdapterOnClickListener = dailyAdapterOnClickListener;
    }

    public interface DailyAdapterOnClickListener{
        void onClick(Daily daily);
    }

    @NonNull
    @Override
    public DailyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.daily_item, viewGroup, false);
        return new DailyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyAdapterViewHolder dailyAdapterViewHolder, int i) {
        dailyAdapterViewHolder.txt_daily_material.setText(dailies[i].getMaterial());
        dailyAdapterViewHolder.txt_daily_unit.setText(dailies[i].getUnit());
        dailyAdapterViewHolder.txt_daily_value.setText(dailies[i].getValue());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class DailyAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_daily_material;
        TextView txt_daily_unit;
        TextView txt_daily_value;

        public DailyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_daily_material = itemView.findViewById(R.id.txt_daily_material);
            txt_daily_unit= itemView.findViewById(R.id.txt_daily_unit);
            txt_daily_value= itemView.findViewById(R.id.txt_daily_value);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();
            Daily daily = dailies[id];
            dailyAdapterOnClickListener.onClick(daily);
        }
    }

    public void setDailies(Daily[] dailies){
        this.dailies = dailies;
        notifyDataSetChanged();
    }

}
