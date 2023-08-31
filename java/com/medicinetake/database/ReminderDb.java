package com.medicinetake.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.medicinetake.db_tables.ReminderMed;

import java.util.ArrayList;

import static com.medicinetake.database.MedTakeDb.ACTIVE;
import static com.medicinetake.database.MedTakeDb.ACTIVE_REP;
import static com.medicinetake.database.MedTakeDb.ID_MED_REM;
import static com.medicinetake.database.MedTakeDb.ID_REMINDER;
import static com.medicinetake.database.MedTakeDb.REPEAT_DAY;
import static com.medicinetake.database.MedTakeDb.REPEAT_MINUTE;
import static com.medicinetake.database.MedTakeDb.TB_REMINDER;
import static com.medicinetake.database.MedTakeDb.TIME;
import static com.medicinetake.database.MedTakeDb.DATE;

public class ReminderDb extends SQL_Db {

    public ReminderDb(Context context) {
        super(context);
    }

    /**
     * insert reminder info
     ***/

    public boolean insertReminderInfo(ReminderMed reminderMed) {
        ContentValues values = new ContentValues();

        values.put(TIME, reminderMed.getTime());
        values.put(DATE, reminderMed.getDate());
        values.put(REPEAT_MINUTE, reminderMed.getRepeat_min());
        values.put(REPEAT_DAY, reminderMed.getRepeat_day());
        values.put(ACTIVE, reminderMed.getActive());
        values.put(ACTIVE_REP, reminderMed.getActive_r());
        values.put(ID_MED_REM, reminderMed.getId_med_rem());


        long rR = db.insert(TB_REMINDER, null, values);

        if (rR != -1) {
            return true;
        } else {
            return false;
        }

    }


    public boolean ifExistReminder(ReminderMed reminderMed) {

        Cursor c = db.rawQuery("SELECT * FROM " + TB_REMINDER +
                        " WHERE " + TIME + " LIKE ? AND " + DATE + " LIKE ? AND " + ID_MED_REM + " LIKE ? ",
                new String[]{reminderMed.getTime(), reminderMed.getDate(), String.valueOf(reminderMed.getId_med_rem())});

        if (c.getCount() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public ReminderMed getReminderId(ReminderMed reminderMed) {

        Cursor c = db.rawQuery("SELECT * FROM " + TB_REMINDER +
                        " WHERE " + TIME + " = ? AND " + DATE + " =  ? AND " + ID_MED_REM + " LIKE ? ",
                new String[]{reminderMed.getTime(), reminderMed.getDate(), String.valueOf(reminderMed.getId_med_rem())});

        if (c.moveToFirst()) {

            int id_Reminder = c.getInt(c.getColumnIndex(ID_REMINDER));

            ReminderMed reminderMed1 = new ReminderMed(id_Reminder);

            c.close();
            return reminderMed1;
        } else {
            return null;
        }

    }


    public ReminderMed getOneReminder(int id_reminder) {

        Cursor c = db.rawQuery("SELECT * FROM " + TB_REMINDER + " WHERE " + ID_REMINDER + " = ? ",
                new String[]{String.valueOf(id_reminder)});

        if (c.moveToFirst()) {
            int id_rem = c.getInt(c.getColumnIndex(ID_REMINDER));
            String time = c.getString(c.getColumnIndex(TIME));
            String date = c.getString(c.getColumnIndex(DATE));
            String repeat_min = c.getString(c.getColumnIndex(REPEAT_MINUTE));
            String repeat_day = c.getString(c.getColumnIndex(REPEAT_DAY));
            String active = c.getString(c.getColumnIndex(ACTIVE));
            String active_r = c.getString(c.getColumnIndex(ACTIVE_REP));
            int id_med_rem = c.getInt(c.getColumnIndex(ID_MED_REM));
            ReminderMed reminderMed1 = new ReminderMed(id_rem, time, date, repeat_min, repeat_day, active, active_r, id_med_rem);

            c.close();
            return reminderMed1;
        } else {
            return null;
        }
    }

    /****get all reminder for one medicine******/

    public ArrayList<ReminderMed> getReminders(int id_med) {

        ArrayList<ReminderMed> reminderMeds = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_REMINDER + " WHERE " + ID_MED_REM + " = ? ",
                new String[]{String.valueOf(id_med)});

        if (c.moveToFirst()) {
            do {
                int id_rem = c.getInt(c.getColumnIndex(ID_REMINDER));
                String time = c.getString(c.getColumnIndex(TIME));
                String date = c.getString(c.getColumnIndex(DATE));
                String repeat_min = c.getString(c.getColumnIndex(REPEAT_MINUTE));
                String repeat_day = c.getString(c.getColumnIndex(REPEAT_DAY));
                String active = c.getString(c.getColumnIndex(ACTIVE));
                String active_r = c.getString(c.getColumnIndex(ACTIVE_REP));
                int id_med_rem = c.getInt(c.getColumnIndex(ID_MED_REM));
                ReminderMed reminderMed1 = new ReminderMed(id_rem, time, date, repeat_min, repeat_day, active, active_r, id_med_rem);
                reminderMeds.add(reminderMed1);
            } while (c.moveToNext());
            c.close();
        }
        return reminderMeds;
    }


    /*****delete reminder*******/

    public boolean deleteReminder(int id_rem) {

        long rDelete = db.delete(TB_REMINDER, ID_REMINDER + " = ? ", new String[]{String.valueOf(id_rem)});

        if (rDelete > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteAllReminder(int id_med) {
        long re = db.delete(TB_REMINDER, ID_MED_REM + " = ? ", new String[]{String.valueOf(id_med)});
        if (re > 0) {
            return true;
        } else {
            return false;
        }
    }

    /***get all identifications of reminder by med_id********/
    public ArrayList<Integer> getAllReminderId(int idMed) {
        ArrayList<Integer> idList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_REMINDER + " WHERE " + ID_MED_REM + " = ? ", new String[]{String.valueOf(idMed)});

        if (c.moveToFirst()) {

            do {

                int idRem = c.getInt(c.getColumnIndex(ID_REMINDER));

                idList.add(idRem);

            } while (c.moveToNext());

            c.close();
        }
        return idList;

    }


    /***update reminder ****************/

    public boolean updateReminder(int id_reminder, ReminderMed reminder) {

        ContentValues values = new ContentValues();

        values.put(TIME, reminder.getTime());
        values.put(DATE, reminder.getDate());
        values.put(REPEAT_MINUTE, reminder.getRepeat_min());
        values.put(REPEAT_DAY, reminder.getRepeat_day());
        values.put(ACTIVE, reminder.getActive());
        values.put(ACTIVE_REP, reminder.getActive_r());

        long r = db.update(TB_REMINDER, values, ID_REMINDER + " = ? ", new String[]{String.valueOf(id_reminder)});
        if (r > 0) {
            return true;
        } else {
            return false;
        }

    }


    /***update active reminder******/
    public boolean updateActiveRem(int id_reminder, ReminderMed reminder) {

        ContentValues values = new ContentValues();

//        values.put(TIME, reminder.getTime());
//        values.put(DATE, reminder.getDate());
//        values.put(REPEAT_MINUTE, reminder.getRepeat_min());
        values.put(REPEAT_DAY, reminder.getRepeat_day());
        values.put(ACTIVE, reminder.getActive());
        values.put(ACTIVE_REP, reminder.getActive_r());

        long r = db.update(TB_REMINDER, values, ID_REMINDER + " = ? ", new String[]{String.valueOf(id_reminder)});
        if (r > 0) {
            return true;
        } else {
            return false;
        }

    }

}



