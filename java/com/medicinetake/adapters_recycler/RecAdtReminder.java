package com.medicinetake.adapters_recycler;

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
import com.medicinetake.db_tables.ReminderMed;
import com.medicinetake.interfaces.OnClickReminder;

import java.util.ArrayList;

public class RecAdtReminder extends RecyclerView.Adapter<RecAdtReminder.HolderReminder> {

    public ArrayList<ReminderMed> reminders = new ArrayList<>();
    public OnClickReminder onClickReminder;


    public RecAdtReminder(ArrayList<ReminderMed> reminders, OnClickReminder onClickReminder) {

        this.reminders = reminders;
        this.onClickReminder = onClickReminder;

    }

    @NonNull
    @Override
    public HolderReminder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HolderReminder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_reminder, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderReminder holder, int position) {

        ReminderMed reminderMed = reminders.get(position);

        holder.tv_time.setText(reminderMed.getTime());
        holder.tv_date.setText(reminderMed.getDate());
        holder.tv_time.setTag(reminderMed.getId_reminder());

        if (reminderMed.getActive().equals("active")) {
            holder.img_yes.setVisibility(View.VISIBLE);
        }

        if (reminderMed.getActive().equals("not active")) {
            holder.img_no.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public class HolderReminder extends RecyclerView.ViewHolder {

        TextView tv_time, tv_date;
        ImageView img_yes, img_no;

        public HolderReminder(@NonNull final View itemView) {
            super(itemView);

            tv_time = itemView.findViewById(R.id.text_time_rem_list);
            tv_date = itemView.findViewById(R.id.text_date_rem_list);

            img_yes = itemView.findViewById(R.id.alert_yes);
            img_no = itemView.findViewById(R.id.alert_no);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    int id_rem = (int) tv_time.getTag();
                    onClickReminder.onClickRem(id_rem);

                    Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_med);
                    itemView.startAnimation(animation);

                }

            });

        }
    }


}
