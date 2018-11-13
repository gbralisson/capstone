package com.example.android.androidlibrary.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.androidlibrary.Model.Material;
import com.example.android.androidlibrary.R;

public class MaterialAdapter extends RecyclerView.Adapter<MaterialAdapter.MaterialAdapterViewModel>{

    private MaterialAdapterOnClickListerner materialAdapterOnClickListerner;
    private Material[] materials;

    public MaterialAdapter(MaterialAdapterOnClickListerner materialAdapterOnClickListerner){
        this.materialAdapterOnClickListerner = materialAdapterOnClickListerner;
    }

    public interface MaterialAdapterOnClickListerner{
        void onClick(Material material);
    }

    @NonNull
    @Override
    public MaterialAdapterViewModel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.material_item, viewGroup, false);
        return new MaterialAdapterViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialAdapterViewModel materialAdapterViewModel, int i) {
        materialAdapterViewModel.txt_material.setText(materials[i].getMaterial());
        materialAdapterViewModel.txt_unit.setText(materials[i].getUnit());
        materialAdapterViewModel.txt_value.setText(materials[i].getValue());
    }

    @Override
    public int getItemCount() {
        if (materials == null)
            return 0;
        return materials.length;
    }

    public class MaterialAdapterViewModel extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_material;
        TextView txt_unit;
        TextView txt_value;

        public MaterialAdapterViewModel(@NonNull View itemView) {
            super(itemView);
            txt_material = itemView.findViewById(R.id.txt_material);
            txt_unit = itemView.findViewById(R.id.txt_unit);
            txt_value = itemView.findViewById(R.id.txt_value);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = getAdapterPosition();
            Material material = materials[id];
            materialAdapterOnClickListerner.onClick(material);
        }
    }

    public void setMaterials(Material[] materials){
        this.materials = materials;
        notifyDataSetChanged();
    }

}
