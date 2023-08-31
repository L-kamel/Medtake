package com.medicinetake.users_activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtReminder;
import com.medicinetake.broadcast.AlarmBroadcast;
import com.medicinetake.database.MedUserDb;
import com.medicinetake.database.ReminderDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.MyMedicines;
import com.medicinetake.db_tables.ReminderMed;
import com.medicinetake.interfaces.OnClickReminder;
import com.medicinetake.my_fragment_work.MmFragment;

import java.util.ArrayList;

import static com.medicinetake.my_fragment_work.mMyMedicines.Id_MED_To_REM_LIST;
import static com.medicinetake.my_fragment_work.mMyMedicines.Id_UsEr_To_REM_LIST;
import static com.medicinetake.my_fragment_work.mMyMedicines.adtMed;

public class ReminderList extends AppCompatActivity {

    /**
     * XML**
     *******/

    private Toolbar toolbar;

    private TextView tv_med_name, tv_med_take, tv_med_form, tv_sub_form, tv_sub_form2, tv_dose;
    private ImageView img_med;

    private RecyclerView rec_reminder;

    private FloatingActionButton floating, float_add_med, float_go_home, float_add_rem;

    /***
     *
     *  this variables help you to manage the code
     *
     */

    private int id_med_f_add_reminder, id_med_f_my_medicines, id_user_f_my_medicines, id_user_f_add_reminder, id_med_f_this, id_user_f_this,
            id_user_f_modify_med, id_med_f_modify_med;
    private int id_med, id_user;

    private RecyclerView.LayoutManager layoutReminder;
    private RecAdtReminder adtReminder;


    private boolean permissionOpenFloatingMenu = true;
    private Float aFloat = 150f;

    /****
     * database
     * *****/

    private UserDb userDb;
    private MedUserDb medDb;
    private ReminderDb reminderDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);

        /***call methods*********/

        initialisation();
        StatusBar();
        getToolbar();
        hideFloatings();
        floatingActionBtn();
        getIdIntent();
        getMedicineInfo();
        getReminderList();
        fAddNewReminder();
        fAddNewMed();
        fGoHome();

    }

    /*****
     * Initialisation
     * ***/

    private void initialisation() {
        toolbar = findViewById(R.id.toolbar_reminder_list);

        tv_med_name = findViewById(R.id.text_med_name_rem_list);
        tv_med_take = findViewById(R.id.text_take_med_rem_list);
        tv_med_form = findViewById(R.id.text_med_form_rem_list);
        tv_sub_form = findViewById(R.id.text_med_sub_form_rem_list);
        tv_sub_form2 = findViewById(R.id.text_med_sub_form2_rem_list);
        tv_dose = findViewById(R.id.text_dose_rem_list);
        img_med = findViewById(R.id.img_med_rem_list);

        floating = findViewById(R.id.float_reminder);
        float_go_home = findViewById(R.id.float_go_home);
        float_add_med = findViewById(R.id.float_add_med);
        float_add_rem = findViewById(R.id.float_add_reminder);

        rec_reminder = findViewById(R.id.rec_reminder_list);


        userDb = new UserDb(getBaseContext());
        medDb = new MedUserDb(getBaseContext());
        reminderDb = new ReminderDb(getBaseContext());

    }


    /**
     * Toolbar and status bar options
     ***/

    private void StatusBar() {

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentAddMed));
        }
    }


    private void getToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.modify_medicines_list:
                Intent iMed = new Intent(getBaseContext(), AddMedicines.class);
                iMed.putExtra("id user in reminder list to add med", id_user);
                iMed.putExtra("id med in reminder list to modify med", id_med);
                startActivity(iMed);

                return true;

            case R.id.delete_medicine_list:
                deleteMedicine();
                return true;

            case R.id.delete_reminders_list:
                deleteAllReminders();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reminder_list_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }


    /***get floating actionButton work****/


    private void floatingActionBtn() {
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (permissionOpenFloatingMenu) {
                    openFloatingMenu();
                } else {
                    closeFloatingMenu();
                }
            }
        });
    }

    //open Floating menu
    private void openFloatingMenu() {

        floating.animate().rotation(135f).setDuration(400).start();

        float_go_home.animate().translationY(0f).alpha(1f).setDuration(500).start();
        float_add_med.animate().translationY(0f).alpha(1f).setDuration(500).start();
        float_add_rem.animate().translationY(0f).alpha(1f).setDuration(500).start();

        permissionOpenFloatingMenu = false;
    }

    //close Floating menu
    private void closeFloatingMenu() {

        floating.animate().rotation(0f).setDuration(400).start();

        float_go_home.animate().translationY(aFloat).alpha(0f).setDuration(500).start();
        float_add_med.animate().translationY(aFloat).alpha(0f).setDuration(500).start();
        float_add_rem.animate().translationY(aFloat).alpha(0f).setDuration(500).start();

        permissionOpenFloatingMenu = true;
    }

    private void hideFloatings() {
        float_go_home.setAlpha(0f);
        float_add_med.setAlpha(0f);
        float_add_rem.setAlpha(0f);

    }

    /**
     * get intent
     ***/

    private void getIdIntent() {
        Intent intent = getIntent();
        id_med_f_add_reminder = intent.getIntExtra("id med to rem list", -1);
        id_user_f_add_reminder = intent.getIntExtra("id user to rem list", -1);
        id_user_f_modify_med = intent.getIntExtra(AddMedicines.ID_USER_TO_REMINDER, -1);
        id_med_f_modify_med = intent.getIntExtra(AddMedicines.ID_MED_TO_REMINDER, -1);


        id_med_f_my_medicines = intent.getIntExtra(Id_MED_To_REM_LIST, -1);
        id_user_f_my_medicines = intent.getIntExtra(Id_UsEr_To_REM_LIST, -1);

        id_med_f_this = intent.getIntExtra("id med from this", -1);
        id_user_f_this = intent.getIntExtra("id user from this", -1);

        if (id_med_f_add_reminder != -1) {
            id_med = id_med_f_add_reminder;
        }

        if (id_med_f_my_medicines != -1) {
            id_med = id_med_f_my_medicines;
        }
        if (id_med_f_modify_med != -1) {
            id_med = id_med_f_modify_med;
        }
        if (id_med_f_this != -1) {
            id_med = id_med_f_this;
        }

        if (id_user_f_add_reminder != -1) {
            id_user = id_user_f_add_reminder;
        }

        if (id_user_f_my_medicines != -1) {
            id_user = id_user_f_my_medicines;
        }
        if (id_user_f_modify_med != -1) {
            id_user = id_user_f_modify_med;
        }
        if (id_user_f_this != -1) {
            id_user = id_user_f_this;
        }

    }

    /**
     * get medicine info
     *****/

    private void getMedicineInfo() {
        medDb.OpenDb();
        MyMedicines myMedicines = medDb.getOneMedicine(id_med);

        tv_med_name.setText(myMedicines.getMed_name());
        tv_med_take.setText("Take " + myMedicines.getMed_take());
        tv_med_form.setText(myMedicines.getMed_form());
        tv_sub_form.setText(myMedicines.getMed_sub_form());
        tv_sub_form2.setText(myMedicines.getMed_sub_form2());
        if (myMedicines.getMed_image() != null) {
            img_med.setImageBitmap(BitmapFactory.decodeByteArray(myMedicines.getMed_image(), 0, myMedicines.getMed_image().length));
        }
        if (myMedicines.getMed_sub_form().equals("Dried")) {
            tv_dose.setText(myMedicines.getMed_dose() + " mg");
        } else if (myMedicines.getMed_sub_form().equals("Liquid")) {
            tv_dose.setText(myMedicines.getMed_dose() + " ml");
        }


        medDb.CloseDb();

    }


    /***get reminder list *******/

    private void getReminderList() {
        ArrayList<ReminderMed> reminders = new ArrayList<>();
        reminderDb.OpenDb();
        reminders = reminderDb.getReminders(id_med);
        adtReminder = new RecAdtReminder(reminders, new OnClickReminder() {
            @Override
            public void onClickRem(int id_rem) {
                // Toast.makeText(ReminderList.this, "" + id_rem, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), AlarmReminder.class);
                intent.putExtra("id reminder Reminder list", id_rem);
                intent.putExtra("id med in reminder list", id_med);
                intent.putExtra("id user in reminder list", id_user);
                startActivity(intent);

            }
        });
        layoutReminder = new LinearLayoutManager(this);
        rec_reminder.setAdapter(adtReminder);
        rec_reminder.setLayoutManager(layoutReminder);
        reminderDb.CloseDb();
    }


    /****get the floatingsActionButtons Work*******/

    private void fAddNewReminder() {
        float_add_rem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iRem = new Intent(getBaseContext(), AlarmReminder.class);
                iRem.putExtra("id med in reminder list", id_med);
                iRem.putExtra("id user in reminder list", id_user);
                startActivity(iRem);
                closeFloatingMenu();
            }
        });
    }

    private void fAddNewMed() {
        float_add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iMed = new Intent(getBaseContext(), AddMedicines.class);
                iMed.putExtra("id user in reminder list to add med", id_user);
                startActivity(iMed);
                closeFloatingMenu();
            }
        });
    }


    private void fGoHome() {
        float_go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iHome = new Intent(getBaseContext(), MmFragment.class);
                iHome.putExtra("id user go home", id_user);
                startActivity(iHome);
            }
        });
    }


    /**
     * delete medicine
     ***/

    private void deleteMedicine() {


        AlertDialog.Builder bAlert = new AlertDialog.Builder(this);
        bAlert.setTitle("Delete Medicine").setMessage("\n").setMessage("Do you want to delete this med ?");

        bAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean rM, rR;
                ArrayList<Integer> idRemList = new ArrayList<>();
                reminderDb.OpenDb();
                medDb.OpenDb();
                idRemList = reminderDb.getAllReminderId(id_med);
                rR = reminderDb.deleteAllReminder(id_med);
                rM = medDb.deleteMed(id_med);
                if (rM) {
                    for (int id : idRemList) {
                        cancelAlarm(id);
                    }

                    Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), MmFragment.class);
                    intent.putExtra("id user for refreshing recyclerView", id_user);
                    startActivity(intent);
                    finish();

                } else {

                }
                medDb.CloseDb();
                reminderDb.CloseDb();


            }
        });

        bAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });


        AlertDialog alertD = bAlert.create();
        alertD.show();


    }

    /****deleteAll reminders********/

    private void deleteAllReminders() {


        AlertDialog.Builder bAlert = new AlertDialog.Builder(this);
        bAlert.setTitle("Delete Reminders").setMessage("\n").setMessage("Do you want to delete these reminders ?");

        bAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean rR;
                ArrayList<Integer> idRemList = new ArrayList<>();
                reminderDb.OpenDb();
                idRemList = reminderDb.getAllReminderId(id_med);
                rR = reminderDb.deleteAllReminder(id_med);
                if (rR) {

                    for (int id : idRemList) {
                        cancelAlarm(id);
                    }

                    Toast.makeText(getBaseContext(), "Deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getBaseContext(), ReminderList.class);
                    intent.putExtra("id med from this", id_med);
                    intent.putExtra("id user from this", id_user);
                    startActivity(intent);
                    finish();


                } else {

                }
                reminderDb.CloseDb();


            }
        });

        bAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getBaseContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });


        AlertDialog alertD = bAlert.create();
        alertD.show();


    }

    private void cancelAlarm(int id_med) {

        AlarmManager aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent iBroadcast = new Intent(getBaseContext(), AlarmBroadcast.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), id_med, iBroadcast, PendingIntent.FLAG_CANCEL_CURRENT);
        aManager.cancel(pIntent);
        Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show();
    }

}
