package com.medicinetake.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.medicinetake.R;
import com.medicinetake.users_activities.TakeMedicines;

public class AlarmNtf extends ContextWrapper {

    public static String ID_NTF_CHANNEL = "id ntf channel";
    public static String NAME_NTF_CHANNEL = "name ntf channel";
    private NotificationChannel ntfChannel;
    private NotificationManager ntfManager;


    public AlarmNtf(Context base) {
        super(base);

        CrtNtfChannel();

    }


    public void CrtNtfChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ntfChannel = new NotificationChannel(ID_NTF_CHANNEL, NAME_NTF_CHANNEL, NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NtfManager().createNotificationChannel(ntfChannel);
        }
    }


    public NotificationManager NtfManager() {
        if (ntfManager == null) {
            ntfManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return ntfManager;
    }

    public NotificationCompat.Builder NtfCompatBlr(String username, int id_user, int id_reminder, String t, String d) {

        NotificationCompat.Builder nBuilder = new
                NotificationCompat.Builder(getApplicationContext(), ID_NTF_CHANNEL)
                .setContentTitle("Hello " + username + "!")
                .setContentText("Time to take your Meds " + t)
                .setSmallIcon(R.drawable.ic_pills22)
                .setAutoCancel(true);

        Intent iTake = new Intent(getBaseContext(), TakeMedicines.class);
        iTake.putExtra("take id user", id_user);
        iTake.putExtra("take id reminder", id_reminder);
        iTake.putExtra("take time reminder", t);
        iTake.putExtra("take date reminder", d);

        PendingIntent pendActivity =
                PendingIntent.getActivity(getApplicationContext(),
                        id_user, iTake, PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder.setContentIntent(pendActivity);

        return nBuilder;

    }

}
