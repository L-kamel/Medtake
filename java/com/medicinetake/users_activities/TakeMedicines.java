package com.medicinetake.users_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtTakeMed;
import com.medicinetake.database.HistoryDb;
import com.medicinetake.database.MedUserDb;
import com.medicinetake.database.ReminderDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.Histories;
import com.medicinetake.db_tables.MyMedicines;
import com.medicinetake.db_tables.ReminderMed;
import com.medicinetake.db_tables.User;

import java.util.ArrayList;

public class TakeMedicines extends AppCompatActivity {

    /****
     *XML
     ****/
    private RecyclerView recycler_take_med;
    private TextView tv_username, tv_time, tv_date;

    private FloatingActionButton floatingTake, floatingCancel;

    /**
     * database
     ***/

    private UserDb userDb;
    private MedUserDb medDb;
    private ReminderDb reminderDb;
    private HistoryDb historyDb;


    /***this help you to manage the code*****/
    private int id_user, id_rem;
    private String T, D;

    private RecAdtTakeMed adtTakeMed;
    private RecyclerView.LayoutManager layoutTake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_medicines);

        /***call methods******/
        StatusBar();
        getIdByIntents();
        initialisation();

        getInfoUserRem();

        floatingTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeMedicines();
            }
        });


    }

    /***status bar**/
    private void StatusBar() {

        if (android.os.Build.VERSION.SDK_INT >= 21) {

            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentAddMed));

        }
    }

    /***get intent********/
    private void getIdByIntents() {

        Intent intent = getIntent();
        id_user = intent.getIntExtra("take id user", -1);
        id_rem = intent.getIntExtra("take id reminder", -1);
        T = intent.getStringExtra("take time reminder");
         D = intent.getStringExtra("take date reminder");

    }

    /***initialisations********/
    private void initialisation() {
        tv_username = findViewById(R.id.text_username_take);
        tv_time = findViewById(R.id.text_time_take_medicine);
      //  tv_date = findViewById(R.id.text_date_take_medicine);
        recycler_take_med = findViewById(R.id.rec_take_medicine);

        floatingTake = findViewById(R.id.float_take);

        userDb = new UserDb(this);
        medDb = new MedUserDb(this);
        reminderDb = new ReminderDb(this);
        historyDb = new HistoryDb(this);


    }

    /**
     * get user info and reminder info and medicines
     ****/
    private void getInfoUserRem() {

        userDb.OpenDb();
        medDb.OpenDb();
        reminderDb.OpenDb();
        historyDb.OpenDb();

        User user = userDb.getUser(id_user);
        ReminderMed reminderMed = reminderDb.getOneReminder(id_rem);
        ArrayList<MyMedicines> myMedicines = medDb.getMedicinesForTake(id_user, T, D);

        tv_username.setText("Hello! " + user.getUsername() + " don't forget to take these meds");
        tv_time.setText(reminderMed.getTime());
      //  tv_date.setText(reminderMed.getDate());

        adtTakeMed = new RecAdtTakeMed(myMedicines);
        layoutTake = new LinearLayoutManager(this);

        userDb.CloseDb();
        medDb.CloseDb();
        reminderDb.CloseDb();
        historyDb.CloseDb();

        recycler_take_med.setAdapter(adtTakeMed);
        recycler_take_med.setLayoutManager(layoutTake);

    }

    /***save take history*****/

    private void takeMedicines() {

        userDb.OpenDb();
        medDb.OpenDb();
        reminderDb.OpenDb();
        historyDb.OpenDb();

        ReminderMed reminderMed = reminderDb.getOneReminder(id_rem);
        ArrayList<MyMedicines> myMedicines = medDb.getMedicinesForTake(id_user, T, D);

        String time_h = T;
        String date_h = D;
        String take = "take";
        int id_user_h = id_user;

        for (int i = 0; i < myMedicines.size(); i++) {

            MyMedicines myMed = myMedicines.get(i);


            String med_h = myMed.getMed_name();
            int id_med_h = myMed.getId_med();

            Histories histories = new Histories(med_h, time_h, date_h, take, id_med_h, id_user_h);
            if (historyDb.ifTookExist(med_h, time_h, date_h, id_med_h, id_user_h)) {
            } else {
                boolean r = historyDb.insertHistoricalInfo(histories);

                if (r) {
                    Toast.makeText(this, " Taken ", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Not taken", Toast.LENGTH_SHORT).show();
                }
            }

        }


        userDb.OpenDb();
        medDb.OpenDb();
        reminderDb.OpenDb();
        historyDb.OpenDb();

        finish();
    }


}