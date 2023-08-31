package com.medicinetake.adapters_recycler;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinetake.R;
import com.medicinetake.db_tables.MyMedicines;
import com.medicinetake.interfaces.OnClickMed;

import java.util.ArrayList;

public class RecAdtMed extends RecyclerView.Adapter<RecAdtMed.MedicineHolder> {

    public ArrayList<MyMedicines> myMedicines = new ArrayList<>();
    public OnClickMed onClick;

    public RecAdtMed(ArrayList<MyMedicines> myMedicines, OnClickMed onClick) {
        this.myMedicines = myMedicines;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MedicineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new MedicineHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_my_medicines, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineHolder holder, int position) {
        MyMedicines myMedicine = myMedicines.get(position);

        holder.tv_med_name.setText(myMedicine.getMed_name());
        holder.tv_med_form.setText(myMedicine.getMed_form());
        if (myMedicine.getMed_image() != null) {
            holder.img_med_list.setImageBitmap(BitmapFactory.decodeByteArray(myMedicine.getMed_image(),
                    0, myMedicine.getMed_image().length));
        }
        holder.tv_med_name.setTag(myMedicine.getId_med());

    }

    @Override
    public int getItemCount() {
        return myMedicines.size();
    }

    public class MedicineHolder extends RecyclerView.ViewHolder {

        public TextView tv_med_name, tv_med_form;
        public ImageView img_med_list;

        public MedicineHolder(@NonNull final View itemView) {
            super(itemView);
            tv_med_name = itemView.findViewById(R.id.text_med_name_in_my_list_med);
            tv_med_form = itemView.findViewById(R.id.text_form_list_med);
            img_med_list = itemView.findViewById(R.id.img_med_in_my_med_list);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id_m = (int) tv_med_name.getTag();
                    onClick.onClickMed(id_m);

                    Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_med);
                    itemView.startAnimation(animation);

                }
            });

        }
    }

}
