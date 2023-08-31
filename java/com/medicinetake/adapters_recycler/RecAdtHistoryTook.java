package com.medicinetake.adapters_recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinetake.R;
import com.medicinetake.database.HistoryDb;
import com.medicinetake.db_tables.Histories;
import com.medicinetake.db_tables.MyMedicines;

import java.util.ArrayList;

public class RecAdtHistoryTook extends RecyclerView.Adapter<RecAdtHistoryTook.HolderHistoryMed> {


    ArrayList<Histories> myMedicines = new ArrayList<>();

    public RecAdtHistoryTook(ArrayList<Histories> myMedicines) {
        this.myMedicines = myMedicines;
    }

    @NonNull
    @Override
    public HolderHistoryMed onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderHistoryMed(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.holder_history_took, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderHistoryMed holder, int position) {

        Histories myMed = myMedicines.get(position);

        holder.tv_med_name.setText(myMed.getMed_name_h());
        holder.tv_date.setText(myMed.getDate_h());
        holder.tv_time .setText(myMed.getTime_h());


    }

    @Override
    public int getItemCount() {
        return myMedicines.size();
    }

    public class HolderHistoryMed extends RecyclerView.ViewHolder {

        public TextView tv_med_name, tv_date, tv_time;

        public HolderHistoryMed(@NonNull View itemView) {
            super(itemView);

            tv_med_name = itemView.findViewById(R.id.text_history_took_med);
            tv_time = itemView.findViewById(R.id.text_history_took_time);
            tv_date = itemView.findViewById(R.id.text_history_took_date);
        }
    }


}
