package com.medicinetake.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.medicinetake.database.MedUserDb;
import com.medicinetake.database.ReminderDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.MyMedicines;
import com.medicinetake.db_tables.ReminderMed;
import com.medicinetake.db_tables.User;
import com.medicinetake.notifications.AlarmNtf;

public class AlarmBroadcast extends BroadcastReceiver {

    private AlarmNtf alarmNtf;
    private int id_user, id_med, id_reminder;
    private UserDb userDb;
    private MedUserDb medDb;
    private ReminderDb reminderDb;

    @Override
    public void onReceive(Context context, Intent intent) {

        /****
         *
         * this for getting data
         *
         **/

        id_user = intent.getIntExtra("id user", -1);
        id_med = intent.getIntExtra("id med", -1);
        id_reminder = intent.getIntExtra("id reminder", -1);

        userDb = new UserDb(context);
        medDb = new MedUserDb(context);
        reminderDb = new ReminderDb(context);

        userDb.OpenDb();
        medDb.OpenDb();
        reminderDb.OpenDb();

        User user = userDb.getUser(id_user);
        MyMedicines myMedicines = medDb.getOneMedicine(id_med);
        ReminderMed reminderMed = reminderDb.getOneReminder(id_reminder);

        String username = user.getUsername();

        String Time = reminderMed.getTime();
        String Date = reminderMed.getDate();

        userDb.CloseDb();
        medDb.CloseDb();
        reminderDb.CloseDb();


        /***this for deliver the notification*******/

        alarmNtf = new AlarmNtf(context);
        NotificationCompat.Builder b = alarmNtf.NtfCompatBlr(username, id_user, id_reminder,Time,Date);
        alarmNtf.NtfManager().notify(id_user, b.build());

    }
}
