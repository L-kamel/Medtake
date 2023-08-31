package com.medicinetake.adapters_recycler;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinetake.R;
import com.medicinetake.db_tables.MyMedicines;

import java.util.ArrayList;

public class RecAdtTakeMed extends RecyclerView.Adapter<RecAdtTakeMed.HolderTakeMed> {

    public ArrayList<MyMedicines> myMedicines = new ArrayList<>();

    public RecAdtTakeMed(ArrayList<MyMedicines> myMedicines) {
        this.myMedicines = myMedicines;
    }

    @NonNull
    @Override
    public HolderTakeMed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderTakeMed(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_med_take, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTakeMed holder, int position) {
        MyMedicines medicines = myMedicines.get(position);
        holder.tv_med_name.setText(medicines.getMed_name());
        holder.tv_take_med.setText(medicines.getMed_take());

        if(medicines.getMed_sub_form().equals("Dried")){
            holder.tv_dose_med.setText(medicines.getMed_dose()+" mg");
        }else if (medicines.getMed_sub_form().equals("Liquid")){
            holder.tv_dose_med.setText(medicines.getMed_dose()+" ml");
        }

        if (medicines.getMed_image() != null) {
            holder.img_take_med.setImageBitmap(BitmapFactory.decodeByteArray(medicines.getMed_image(),
                    0, medicines.getMed_image().length));
        }
    }

    @Override
    public int getItemCount() {
        return myMedicines.size();
    }

    public class HolderTakeMed extends RecyclerView.ViewHolder {

        public ImageView img_take_med;
        public TextView tv_med_name, tv_take_med, tv_dose_med;


        public HolderTakeMed(@NonNull View itemView) {
            super(itemView);

            img_take_med = itemView.findViewById(R.id.img_take_med);
            tv_med_name = itemView.findViewById(R.id.text_take_med_name);
            tv_take_med = itemView.findViewById(R.id.text_take_med);
            tv_dose_med = itemView.findViewById(R.id.text_take_med_dose);

        }
    }

}
