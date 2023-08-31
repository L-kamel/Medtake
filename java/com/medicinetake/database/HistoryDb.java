package com.medicinetake.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.medicinetake.db_tables.Histories;
import com.medicinetake.db_tables.MyMedicines;
import com.medicinetake.users_activities.History;

import java.util.ArrayList;

import static com.medicinetake.database.MedTakeDb.ACTIVE;
import static com.medicinetake.database.MedTakeDb.DATE;
import static com.medicinetake.database.MedTakeDb.DATE_HISTORY;
import static com.medicinetake.database.MedTakeDb.ID_HISTORY;
import static com.medicinetake.database.MedTakeDb.ID_MEDICINE;
import static com.medicinetake.database.MedTakeDb.ID_MED_HISTORY;
import static com.medicinetake.database.MedTakeDb.ID_MED_REM;
import static com.medicinetake.database.MedTakeDb.ID_USER_HISTORY;
import static com.medicinetake.database.MedTakeDb.MED_DOSE;
import static com.medicinetake.database.MedTakeDb.MED_FORM;
import static com.medicinetake.database.MedTakeDb.MED_IMAGE;
import static com.medicinetake.database.MedTakeDb.MED_NAME;
import static com.medicinetake.database.MedTakeDb.MED_NAME_HISTORY;
import static com.medicinetake.database.MedTakeDb.MED_SUB_FORM;
import static com.medicinetake.database.MedTakeDb.MED_SUB_FORM2;
import static com.medicinetake.database.MedTakeDb.MED_TAKE;
import static com.medicinetake.database.MedTakeDb.MED_USER_ID;
import static com.medicinetake.database.MedTakeDb.TAKE_HISTORY;
import static com.medicinetake.database.MedTakeDb.TB_HISTORY;
import static com.medicinetake.database.MedTakeDb.TB_MY_MEDICINES;
import static com.medicinetake.database.MedTakeDb.TB_REMINDER;
import static com.medicinetake.database.MedTakeDb.TIME_HISTORY;

public class HistoryDb extends SQL_Db {


    public HistoryDb(Context context) {
        super(context);
    }


    public boolean insertHistoricalInfo(Histories histories) {

        ContentValues values = new ContentValues();

        values.put(MED_NAME_HISTORY, histories.getMed_name_h());
        values.put(TIME_HISTORY, histories.getTime_h());
        values.put(DATE_HISTORY, histories.getDate_h());
        values.put(TAKE_HISTORY, histories.getTake_history());
        values.put(ID_MED_HISTORY, histories.getId_med_history());
        values.put(ID_USER_HISTORY, histories.getId_user_history());

        long r = db.insert(TB_HISTORY, null, values);

        if (r != -1) {
            return true;
        } else {
            return false;
        }


    }

    /****get medicines those took *******/
    public ArrayList<Histories> getMedicinesTaken(int id_user, String take_h) {

        ArrayList<Histories> myHistory = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM "
                        + TB_HISTORY + " WHERE " + ID_USER_HISTORY + " = ? AND " + TAKE_HISTORY + " = ? ",
                new String[]{String.valueOf(id_user), take_h});

        if (c.moveToFirst()) {
            do {

                String med_h = c.getString(c.getColumnIndex(MED_NAME_HISTORY));
                String time_h = c.getString(c.getColumnIndex(TIME_HISTORY));
                String date_h = c.getString(c.getColumnIndex(DATE_HISTORY));
                String take = c.getString(c.getColumnIndex(TAKE_HISTORY));
                int id_med_h = c.getInt(c.getColumnIndex(ID_MED_HISTORY));
                int id_user_h = c.getInt(c.getColumnIndex(ID_USER_HISTORY));

                Histories myH = new Histories(med_h, time_h, date_h, take, id_med_h, id_user_h);
                myHistory.add(myH);

            } while (c.moveToNext());

            c.close();
        }
        return myHistory;
    }


    /******med taken if exist ???*****/

    public boolean ifTookExist(String med_h, String time_h, String date_h, int id_med_h, int id_user) {

        Cursor cursor = db.rawQuery("SELECT * FROM " + TB_HISTORY + " WHERE " + MED_NAME_HISTORY + " LIKE ? AND " +
                        TIME_HISTORY + " LIKE ? AND " + DATE_HISTORY + " LIKE ? AND " + ID_MED_HISTORY + " LIKE ? AND " + ID_USER_HISTORY + " LIKE ? ",
                new String[]{med_h, time_h, date_h, String.valueOf(id_med_h), String.valueOf(id_user)});

        if (cursor.getCount() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    /***clear history***/
    public boolean clearHistory(int id_user) {
        long red = db.delete(TB_HISTORY, ID_USER_HISTORY + " = ? ", new String[]{String.valueOf(id_user)});

        if (red > 0) {
            return true;
        } else {
            return false;
        }
    }

}
