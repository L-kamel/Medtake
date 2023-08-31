package com.medicinetake.users_activities;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.medicinetake.R;
import com.medicinetake.broadcast.AlarmBroadcast;
import com.medicinetake.database.MedUserDb;
import com.medicinetake.database.ReminderDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.MyMedicines;
import com.medicinetake.db_tables.ReminderMed;
import com.medicinetake.dialogs.DateDialog;
import com.medicinetake.dialogs.TimeDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AlarmReminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    /***
     * XML
     * ***/

    public TextView text_time, text_date, text_repeat, text_repeat_day;
    public CardView cardView_swActive, card_time, card_date, card_repeat, card_repeat_day;
    private SwitchCompat sw_repeat, sw_repeat_day, sw_active;
    private Button btn_save;
    private Toolbar toolbar;

    private ImageView img_yes, img_no;


    private MenuItem item_delete;

    /**
     * database
     **/

    private String TIME, DATE, REPEAT_MIN, REPEAT_DAY, ACTIVE, ACTIVE_REP;
    private int repeat_min, repeat_day;
    private int ID_MED;

    private int id_med_f_addMed, id_med_f_rem_list;
    private int id_user_f_addMed, id_user_f_rem_list;
    private int id_user, id_reminder;

    private UserDb userDb;
    private MedUserDb medDb;
    private ReminderDb reminderDb;


    /****
     *
     * this variables helping you to manage  your code
     *
     *****/
    private String[] repeatList = new String[]{"5 minutes", "10 minutes", "15 minutes", "30 minutes"};

    private AlarmManager aManager;
    private PendingIntent pIntent;
    private Intent iBroadcast;


    /***
     * objects from classes i created
     * ***/

    private TimeDialog timeDialog;

    private DateDialog dateDialog;

    private Calendar cCalender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_reminder);

        /****call methods****/
        getIntentMedId();
        initialisation();

        StatusBar();
        getToolbar();

        cardTime();
        cardDate();
        cardRepeat();

        swActive();

        getSwRepeatWork();
        getDayRepeat();

        btnSaveReminder();
    }

    /***Initialisation****/
    private void initialisation() {

        toolbar = findViewById(R.id.toolbar_add_reminder);
        text_time = findViewById(R.id.tv_time);
        text_date = findViewById(R.id.tv_date);
        text_repeat = findViewById(R.id.tv_repeat);
        text_repeat_day = findViewById(R.id.tv_repeat_days);

        img_yes = findViewById(R.id.img_alert_yes);
        img_no = findViewById(R.id.img_alert_no);

        sw_active = findViewById(R.id.switch_active);
        sw_repeat = findViewById(R.id.switch_repeat_active);
        sw_repeat_day = findViewById(R.id.switch_repeat_every_day);

        cardView_swActive = findViewById(R.id.card_swActive);
        card_time = findViewById(R.id.card_time);
        card_date = findViewById(R.id.card_date);
        card_repeat = findViewById(R.id.card_repeat);
        card_repeat_day = findViewById(R.id.card_repeat_days);

        btn_save = findViewById(R.id.btn_save_reminder);

        medDb = new MedUserDb(this);
        reminderDb = new ReminderDb(this);

        text_repeat.setText(repeatList[0]);

        if (id_reminder != -1) {

            cardView_swActive.setVisibility(View.VISIBLE);
            btn_save.setText("Modify");
            cCalender = Calendar.getInstance();

            reminderDb.OpenDb();
            ReminderMed reminder = reminderDb.getOneReminder(id_reminder);

            reminderDb.CloseDb();

            String rTime = reminder.getTime();
            String rDate = reminder.getDate();

            SimpleDateFormat fTime = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat fDate = new SimpleDateFormat("MMM dd, yyyy");


            Date dTime = new Date();
            Date dDate = new Date();


            /**convert String to date**/
            try {
                dTime = fTime.parse(rTime);
                dDate = fDate.parse(rDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String sTime = fTime.format(dTime);
            String sDate = fDate.format(dDate);

            /** Convert Time to integer **/

            long lTime = (long) ((dTime.getTime() / 1000) + 3600);
            //   int lTime = (int) ((dTime.getTime() / 1000));

            int hT = (int) ((lTime / 3600));
            int mT = (int) ((lTime % 3600) / 60);

            int h = hT;
            int m = mT;

            cCalender.set(Calendar.HOUR_OF_DAY, h);
            cCalender.set(Calendar.MINUTE, m);
            cCalender.set(Calendar.SECOND, 0);

            cCalender.setTime(dDate);

            /***Convert Date to integer**/

//            int lD = (dDate.getTime()/)

            TIME = "" + reminder.getTime();
            DATE = "" + reminder.getDate();

            REPEAT_MIN = reminder.getRepeat_min();

            REPEAT_DAY = reminder.getRepeat_day();
            ACTIVE_REP = reminder.getActive_r();
            ACTIVE = reminder.getActive();

            repeat_min = 5 * 60 * 1000;
            repeat_day = 24 * 60 * 60 * 1000;

            //reminder active
            if (reminder.getActive().equals("not active")) {
                sw_active.setChecked(false);
                img_no.setVisibility(View.VISIBLE);
            } else if (reminder.getActive().equals("active")) {
                sw_active.setChecked(true);
                img_yes.setVisibility(View.VISIBLE);
            }

            //repeat minutes
            if (ACTIVE_REP.equals("repeat active")) {
                sw_repeat.setChecked(true);
            } else {
                sw_repeat.setChecked(false);
            }

            //day repeat
            if (REPEAT_DAY.equals("day active")) {
                sw_repeat_day.setChecked(true);
            } else {
                sw_repeat_day.setChecked(false);
            }


        } else {

            cCalender = Calendar.getInstance();
            TIME = "" + DateFormat.getTimeInstance(DateFormat.SHORT).format(25200000);
            DATE = "" + DateFormat.getDateInstance(DateFormat.MEDIUM).format(System.currentTimeMillis());

            REPEAT_MIN = repeatList[0];
            REPEAT_DAY = "day active";
            sw_repeat_day.setChecked(true);
            ACTIVE_REP = "repeat not active";
            ACTIVE = "active";
            repeat_min = 5 * 60 * 1000;
            repeat_day = 24 * 60 * 60 * 1000;

        }

        text_time.setText(TIME);
        text_date.setText(DATE);

    }

    private void StatusBar() {

        if (android.os.Build.VERSION.SDK_INT >= 21) {

            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentAddMed));

        }
    }


    /**
     * toolbar
     **/
    private void getToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        medDb.OpenDb();
        MyMedicines myMedicines = medDb.getOneMedicine(ID_MED);
        toolbar.setTitle(myMedicines.getMed_name());
        medDb.CloseDb();
    }


    /**
     * getting intent
     ***/
    private void getIntentMedId() {
        Intent intent = getIntent();
        id_med_f_addMed = intent.getIntExtra(AddMedicines.ID_MED_TO_REMINDER, -1);
        id_user_f_addMed = intent.getIntExtra(AddMedicines.ID_USER_TO_REMINDER, -1);

        id_med_f_rem_list = intent.getIntExtra("id med in reminder list", -1);
        id_user_f_rem_list = intent.getIntExtra("id user in reminder list", -1);

        id_reminder = intent.getIntExtra("id reminder Reminder list", -1);

        if (id_reminder != -1) {
            Toast.makeText(this, "" + id_reminder, Toast.LENGTH_SHORT).show();
        }
        if (id_med_f_addMed != -1) {
            ID_MED = id_med_f_addMed;
        }
        if (id_med_f_rem_list != -1) {
            ID_MED = id_med_f_rem_list;
        }
        if (id_user_f_addMed != -1) {
            id_user = id_user_f_addMed;
        }
        if (id_user_f_rem_list != -1) {
            id_user = id_user_f_rem_list;
        }

    }

    /**
     * get toolbar menu
     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.delete_reminder:
                deleteReminder();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.alarm_reminder_menu, menu);
        // item_delete = menu.findItem(R.id.delete_reminder);
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * get TimeDialog and DateDialog and repeatDialog
     **/
    private void getTimeDialog() {

        timeDialog = new TimeDialog();
        timeDialog.show(getSupportFragmentManager(), "time");

    }

    /***/
    private void getDateDialog() {
        dateDialog = new DateDialog();
        dateDialog.show(getSupportFragmentManager(), "date");
    }

    /****/
    private void getRepeatDialog() {

        AlertDialog.Builder ab = new AlertDialog.Builder(this);

        ab.setTitle("Repeat");

        ab.setSingleChoiceItems(repeatList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                REPEAT_MIN = repeatList[i];
                if (i == 0) {
                    repeat_min = 5 * 60 * 1000;
                }
                if (i == 1) {
                    repeat_min = 10 * 60 * 1000;
                }
                if (i == 2) {
                    repeat_min = 15 * 60 * 1000;
                }
                if (i == 3) {
                    repeat_min = 30 * 60 * 1000;
                }

            }
        });

        ab.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                text_repeat.setText(REPEAT_MIN);
            }
        });

        AlertDialog alertDialog = ab.create();
        alertDialog.show();

    }


    /*****switch active*********/
    private void swActive() {
        sw_active.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ACTIVE = "active";
                    img_yes.setVisibility(View.VISIBLE);
                    img_no.setVisibility(View.INVISIBLE);
                } else {
                    ACTIVE = "not active";
                    img_yes.setVisibility(View.INVISIBLE);
                    img_no.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    /***get switch repeat minutes work*********/
    private void getSwRepeatWork() {

        sw_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ACTIVE_REP = "repeat active";
                    REPEAT_MIN = repeatList[0];
                    text_repeat.setTextColor(Color.BLUE);
                    card_repeat.setEnabled(true);
                } else {
                    ACTIVE_REP = "repeat not active";
                    text_repeat.setTextColor(R.color.colorAccent);
                    repeat_min = 0;
                    card_repeat.setEnabled(false);
                }
            }
        });

    }


    /**
     * get repeat day
     ********/

    public void getDayRepeat() {
        sw_repeat_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    REPEAT_DAY = "day active";
                    repeat_day = 24 * 60 * 60 * 1000;
                    text_repeat_day.setTextColor(Color.BLUE);
                } else {
                    REPEAT_DAY = "day not active";
                    text_repeat_day.setTextColor(R.color.colorAccent);
                    repeat_day = 0;
                }
            }
        });

    }

    /*********************/
    /***cards click on time and date********/
    private void cardTime() {
        card_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTimeDialog();
            }
        });
    }

    private void cardDate() {
        card_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDateDialog();
            }
        });
    }

    /***card click on repeat ************/
    private void cardRepeat() {
        card_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepeatDialog();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        cCalender.set(Calendar.YEAR, i);
        cCalender.set(Calendar.MONTH, i1);
        cCalender.set(Calendar.DAY_OF_MONTH, i2);

        DATE = "" + DateFormat.getDateInstance(DateFormat.MEDIUM).format(cCalender.getTime());
        text_date.setText(DATE);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        cCalender.set(Calendar.HOUR_OF_DAY, i);
        cCalender.set(Calendar.MINUTE, i1);
        cCalender.set(Calendar.SECOND, 0);

        Toast.makeText(this, "" + i, Toast.LENGTH_SHORT).show();
        TIME = "" + DateFormat.getTimeInstance(DateFormat.SHORT).format(cCalender.getTime());
        text_time.setText(TIME);

    }

    /*** set alarm reminder ********/
    private void setReminder(int id_request) {

        aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        iBroadcast = new Intent(getBaseContext(), AlarmBroadcast.class);

        //sending data to broadcastReceiver//

        iBroadcast.putExtra("id user", id_user);
        iBroadcast.putExtra("id med", ID_MED);
        iBroadcast.putExtra("id reminder", id_request);

        pIntent = PendingIntent.getBroadcast(getApplicationContext(), id_request, iBroadcast, PendingIntent.FLAG_UPDATE_CURRENT);


        if (cCalender.before(Calendar.getInstance())) {
            cCalender.add(Calendar.DATE, 1);
        }


        /****the option of repeating by minutes not working yet****/

        if (REPEAT_DAY.equals("day active")) {

            if (Build.VERSION.SDK_INT >= 19) {
                aManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        cCalender.getTimeInMillis(), repeat_day, pIntent);
            } else if (Build.VERSION.SDK_INT >= 23) {

                aManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        cCalender.getTimeInMillis(), repeat_day, pIntent);
            } else {

                aManager.setRepeating(AlarmManager.RTC_WAKEUP,
                        cCalender.getTimeInMillis(), repeat_day, pIntent);
            }

        } else if (ACTIVE.equals("active")) {
            if (Build.VERSION.SDK_INT >= 19) {
                aManager.setExact(AlarmManager.RTC_WAKEUP, cCalender.getTimeInMillis(), pIntent);
                Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
            } else if (Build.VERSION.SDK_INT >= 23) {

                aManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cCalender.getTimeInMillis(), pIntent);
                Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
            } else {

                aManager.set(AlarmManager.RTC_WAKEUP, cCalender.getTimeInMillis(), pIntent);
                Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * cancel alarm
     *************/

    public void cancelAlarm(int id_rem) {

        aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        iBroadcast = new Intent(getBaseContext(), AlarmBroadcast.class);
        pIntent = PendingIntent.getBroadcast(getApplicationContext(), id_rem, iBroadcast, PendingIntent.FLAG_CANCEL_CURRENT);
        aManager.cancel(pIntent);
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();

    }

    /**
     * this for saving alarm in database and system alarm
     ****/

    private void saveReminder() {
        boolean rRem;

        reminderDb.OpenDb();
        ReminderMed reminderMed = new ReminderMed(TIME, DATE, REPEAT_MIN, REPEAT_DAY, ACTIVE, ACTIVE_REP, ID_MED);


        if (reminderDb.ifExistReminder(reminderMed)) {
            ReminderMed reminderId = reminderDb.getReminderId(reminderMed);
            int idRem = reminderId.getId_reminder();
            setReminder(idRem);
//            Toast.makeText(this, "Time exist", Toast.LENGTH_SHORT).show();
            Intent iRemList = new Intent(getBaseContext(), ReminderList.class);
            iRemList.putExtra("id med to rem list", ID_MED);
            iRemList.putExtra("id user to rem list", id_user);
            startActivity(iRemList);

        } else {
            rRem = reminderDb.insertReminderInfo(reminderMed);
            if (rRem) {
                ReminderMed reminderId = reminderDb.getReminderId(reminderMed);
                int idRem2 = reminderId.getId_reminder();
                setReminder(idRem2);
                Intent iRemList = new Intent(getBaseContext(), ReminderList.class);
                iRemList.putExtra("id med to rem list", ID_MED);
                iRemList.putExtra("id user to rem list", id_user);
                startActivity(iRemList);
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            } else {
            }
        }
        reminderDb.CloseDb();
    }

    /***********/
    private void btnSaveReminder() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (id_reminder != -1) {
                    updateReminder(id_reminder);
                } else {
                    saveReminder();
                }

            }
        });
    }

    /*****update reminder******/

    private void updateReminder(int idR) {

        boolean rUp;

        reminderDb.OpenDb();
        ReminderMed reminderMed = new ReminderMed(TIME, DATE, REPEAT_MIN, REPEAT_DAY, ACTIVE, ACTIVE_REP, ID_MED);
        if (reminderDb.ifExistReminder(reminderMed)) {

            if (ACTIVE.equals("active")) {
                setReminder(idR);
            } else {
                cancelAlarm(idR);
            }

            reminderDb.updateActiveRem(idR, reminderMed);

            Intent iRemList = new Intent(getBaseContext(), ReminderList.class);
            iRemList.putExtra("id med to rem list", ID_MED);
            iRemList.putExtra("id user to rem list", id_user);
            startActivity(iRemList);

        } else {

            rUp = reminderDb.updateReminder(idR, reminderMed);

            if (rUp) {

                if (ACTIVE.equals("active")) {
                    setReminder(idR);
                } else {
                    cancelAlarm(idR);
                }

                Intent iRemList = new Intent(getBaseContext(), ReminderList.class);
                iRemList.putExtra("id med to rem list", ID_MED);
                iRemList.putExtra("id user to rem list", id_user);
                startActivity(iRemList);
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            } else {

            }
        }

        reminderDb.CloseDb();
    }


    /**
     * delete reminder
     *****/
    private void deleteReminder() {
        AlertDialog.Builder aB = new AlertDialog.Builder(this);
        aB.setTitle("Delete Reminder");
        aB.setMessage("This action will be remove this alarm!");

        aB.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                reminderDb.OpenDb();
                boolean r = reminderDb.deleteReminder(id_reminder);
                reminderDb.CloseDb();
                if (r) {
                    cancelAlarm(id_reminder);
                    Toast.makeText(AlarmReminder.this, "deleted", Toast.LENGTH_SHORT).show();
                    Intent iRemList = new Intent(getBaseContext(), ReminderList.class);
                    iRemList.putExtra("id med to rem list", ID_MED);
                    iRemList.putExtra("id user to rem list", id_user);
                    startActivity(iRemList);
                    finish();

                } else {

                }

            }
        });

        aB.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = aB.create();
        alertDialog.show();

    }

}