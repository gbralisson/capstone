package com.example.android.capstone.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.androidlibrary.Model.Reform;
import com.example.android.capstone.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReformAdapter extends RecyclerView.Adapter<ReformAdapter.ReformAdapterViewHolder>{

    private Reform[] reforms;
    private ReformAdapterOnClickHandler handler;

    public ReformAdapter(ReformAdapterOnClickHandler reformAdapterOnClickHandler){
        this.handler = reformAdapterOnClickHandler;
    }

    public interface ReformAdapterOnClickHandler{
        void onClick(Reform reform);
    }

    @NonNull
    @Override
    public ReformAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.reform_item, viewGroup, false);

        return new ReformAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReformAdapterViewHolder reformAdapterViewHolder, int i) {
        reformAdapterViewHolder.room.setText(reforms[i].getRoom());
        reformAdapterViewHolder.days.setText(reforms[i].getDays());
        reformAdapterViewHolder.spent.setText(reforms[i].getTotal_spent());
    }

    @Override
    public int getItemCount() {
        if (reforms == null)
            return 0;
        return reforms.length;
    }

    public class ReformAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

//        @BindView(R.id.txt_room) TextView room;
//        @BindView(R.id.txt_days) TextView days;
//        @BindView(R.id.txt_spent) TextView spent;

        TextView room;
        TextView days;
        TextView spent;

        public ReformAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
//            ButterKnife.bind(itemView);
            room = itemView.findViewById(R.id.txt_room);
            days = itemView.findViewById(R.id.txt_days);
            spent = itemView.findViewById(R.id.txt_spent);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();
            Reform reform = reforms[id];
            handler.onClick(reform);
        }
    }

    public void setReforms(Reform[] reforms){
        this.reforms = reforms;
        notifyDataSetChanged();
    }

}
