package com.medicinetake.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.medicinetake.db_tables.MyMedicines;

import java.util.ArrayList;

import static com.medicinetake.database.MedTakeDb.ACTIVE;
import static com.medicinetake.database.MedTakeDb.DATE;
import static com.medicinetake.database.MedTakeDb.ID_MEDICINE;
import static com.medicinetake.database.MedTakeDb.ID_MED_REM;
import static com.medicinetake.database.MedTakeDb.MED_DOSE;
import static com.medicinetake.database.MedTakeDb.MED_FORM;
import static com.medicinetake.database.MedTakeDb.MED_IMAGE;
import static com.medicinetake.database.MedTakeDb.MED_NAME;
import static com.medicinetake.database.MedTakeDb.MED_SUB_FORM;
import static com.medicinetake.database.MedTakeDb.MED_SUB_FORM2;
import static com.medicinetake.database.MedTakeDb.MED_TAKE;
import static com.medicinetake.database.MedTakeDb.MED_USER_ID;
import static com.medicinetake.database.MedTakeDb.TB_MY_MEDICINES;
import static com.medicinetake.database.MedTakeDb.TB_REMINDER;
import static com.medicinetake.database.MedTakeDb.TIME;

public class MedUserDb extends SQL_Db {
    public MedUserDb(Context context) {
        super(context);
    }

    /**
     * insert medicines info
     **/
    public boolean saveMedicine(MyMedicines myMedicines) {

        ContentValues values = new ContentValues();

        values.put(MED_NAME, myMedicines.getMed_name());
        values.put(MED_TAKE, myMedicines.getMed_take());
        values.put(MED_FORM, myMedicines.getMed_form());
        values.put(MED_SUB_FORM, myMedicines.getMed_sub_form());
        values.put(MED_SUB_FORM2, myMedicines.getMed_sub_form2());
        values.put(MED_DOSE, myMedicines.getMed_dose());
        values.put(MED_IMAGE, myMedicines.getMed_image());
        values.put(MED_USER_ID, myMedicines.getMed_user_id());

        long rSave = db.insert(TB_MY_MEDICINES, null, values);

        if (rSave != -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * test if med existing
     *****/
    public boolean medExist(MyMedicines myMedicines) {
        Cursor c = db.rawQuery("SELECT * FROM " + TB_MY_MEDICINES + " WHERE " + MED_NAME + " LIKE ? AND " + MED_USER_ID + " LIKE ? ",
                new String[]{myMedicines.getMed_name(), String.valueOf(myMedicines.getMed_user_id())});
        if (c.getCount() <= 0) {
            return false;
        } else {
            return true;
        }
    }


    /***get medicine  id***/
    public MyMedicines getMedicineId(MyMedicines medicines) {

        Cursor c = db.rawQuery("SELECT * FROM "
                        + TB_MY_MEDICINES + " WHERE " + MED_NAME + " = ? AND " + MED_USER_ID + " = ? ",
                new String[]{medicines.getMed_name(), String.valueOf(medicines.getMed_user_id())});

        if (c.moveToFirst()) {

            int id_med = c.getInt(c.getColumnIndex(ID_MEDICINE));
            String med_name = c.getString(c.getColumnIndex(MED_NAME));

            MyMedicines myMedicines = new MyMedicines(id_med, med_name);

            c.close();

            return myMedicines;
        } else {
            return null;
        }


    }


    /***get medicine  id***/
    public MyMedicines getOneMedicine(int id) {

        Cursor c = db.rawQuery("SELECT * FROM "
                + TB_MY_MEDICINES + " WHERE " + ID_MEDICINE + " = ?", new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {

            int id_med = c.getInt(c.getColumnIndex(ID_MEDICINE));
            String med_name = c.getString(c.getColumnIndex(MED_NAME));
            String med_take = c.getString(c.getColumnIndex(MED_TAKE));
            String med_form = c.getString(c.getColumnIndex(MED_FORM));
            String med_sub_form = c.getString(c.getColumnIndex(MED_SUB_FORM));
            String med_sub_form2 = c.getString(c.getColumnIndex(MED_SUB_FORM2));
            String dose = c.getString(c.getColumnIndex(MED_DOSE));
            byte[] img_med = c.getBlob(c.getColumnIndex(MED_IMAGE));
            int med_id_user = c.getInt(c.getColumnIndex(MED_USER_ID));

            MyMedicines myMedicines = new MyMedicines(id_med, med_name, med_take, med_form, med_sub_form, med_sub_form2, dose, img_med, med_id_user);

            c.close();

            return myMedicines;
        } else {
            return null;
        }


    }

    /**
     * get all medicines
     ***/
    public ArrayList<MyMedicines> getAllMedicines(int id_user) {

        ArrayList<MyMedicines> myMedic = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM "
                + TB_MY_MEDICINES + " WHERE " + MED_USER_ID + " = ? ", new String[]{String.valueOf(id_user)});

//        Cursor c = db.rawQuery("SELECT * FROM "
//                        + TB_MY_MEDICINES + " INNER JOIN " + TB_REMINDER +
//                        " ON " + ID_MEDICINE + " = " + ID_MED_REM +
//                        " WHERE " + MED_USER_ID + " = ? AND " + ACTIVE + " ='active' ",
//                new String[]{String.valueOf(id_user)});


        if (c.moveToFirst()) {
            do {

                int id_med = c.getInt(c.getColumnIndex(ID_MEDICINE));
                String med_name = c.getString(c.getColumnIndex(MED_NAME));
                String med_take = c.getString(c.getColumnIndex(MED_TAKE));
                String med_form = c.getString(c.getColumnIndex(MED_FORM));
                String med_sub_f = c.getString(c.getColumnIndex(MED_SUB_FORM));
                String med_sub_f2 = c.getString(c.getColumnIndex(MED_SUB_FORM2));
                String med_dose = c.getString(c.getColumnIndex(MED_DOSE));
                byte[] med_img = c.getBlob(c.getColumnIndex(MED_IMAGE));
                int id_med_user = c.getInt(c.getColumnIndex(MED_USER_ID));

                MyMedicines myMedicines = new MyMedicines(id_med, med_name, med_take,
                        med_form, med_sub_f, med_sub_f2, med_dose, med_img, id_med_user);
                myMedic.add(myMedicines);

            } while (c.moveToNext());

            c.close();
        }
        return myMedic;
    }


    /**
     * get all to take medicines
     ***/
    public ArrayList<MyMedicines> getMedicinesForTake(int id_user, String t, String d) {

        ArrayList<MyMedicines> myMedic = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM "
                        + TB_MY_MEDICINES + " INNER JOIN " + TB_REMINDER +
                        " ON " + ID_MEDICINE + " = " + ID_MED_REM +
                        " WHERE " + MED_USER_ID + " = ? AND " + TIME + " = ? AND " + DATE + " = ? AND " + ACTIVE + " = 'active' ",
                new String[]{String.valueOf(id_user), t, d});


        if (c.moveToFirst()) {
            do {

                int id_med = c.getInt(c.getColumnIndex(ID_MEDICINE));
                String med_name = c.getString(c.getColumnIndex(MED_NAME));
                String med_take = c.getString(c.getColumnIndex(MED_TAKE));
                String med_form = c.getString(c.getColumnIndex(MED_FORM));
                String med_sub_f = c.getString(c.getColumnIndex(MED_SUB_FORM));
                String med_sub_f2 = c.getString(c.getColumnIndex(MED_SUB_FORM2));
                String med_dose = c.getString(c.getColumnIndex(MED_DOSE));
                byte[] med_img = c.getBlob(c.getColumnIndex(MED_IMAGE));
                int id_med_user = c.getInt(c.getColumnIndex(MED_USER_ID));

                MyMedicines myMedicines = new MyMedicines(id_med, med_name, med_take,
                        med_form, med_sub_f, med_sub_f2, med_dose, med_img, id_med_user);
                myMedic.add(myMedicines);

            } while (c.moveToNext());

            c.close();
        }
        return myMedic;
    }

    /**
     * this for delete medicine by id med
     ******/

    public boolean deleteMed(int id_med) {

        long red = db.delete(TB_MY_MEDICINES, ID_MEDICINE + " = ? ", new String[]{String.valueOf(id_med)});

        if (red > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete all medicine by user id
     ***************/
    public boolean deleteAllMedByUser(int id_user) {

        long red = db.delete(TB_MY_MEDICINES, MED_USER_ID + " = ? ", new String[]{String.valueOf(id_user)});

        if (red > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * get all identifications of user in medicines table
     *******/

    public ArrayList<Integer> getAllIdMedicine(int idUser) {

        ArrayList<Integer> idList = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_MY_MEDICINES + " WHERE " + MED_USER_ID + " = ? ",
                new String[]{String.valueOf(idUser)});

        if (c.moveToFirst()) {
            do {
                int idM = c.getInt(c.getColumnIndex(ID_MEDICINE));
                idList.add(idM);
            } while (c.moveToNext());
            c.close();
        }
        return idList;
    }

    public boolean modifyMed(MyMedicines myMedicines) {

        ContentValues values = new ContentValues();

        values.put(MED_NAME, myMedicines.getMed_name());
        values.put(MED_TAKE, myMedicines.getMed_take());
        values.put(MED_FORM, myMedicines.getMed_form());
        values.put(MED_SUB_FORM, myMedicines.getMed_sub_form());
        values.put(MED_SUB_FORM2, myMedicines.getMed_sub_form2());
        values.put(MED_DOSE, myMedicines.getMed_dose());
        values.put(MED_IMAGE, myMedicines.getMed_image());
        values.put(MED_USER_ID, myMedicines.getMed_user_id());

        long upRes = db.update(TB_MY_MEDICINES, values, ID_MEDICINE + " = ?", new String[]{String.valueOf(myMedicines.getId_med())});

        if (upRes > 0) {
            return true;
        } else {
            return false;
        }


    }


    public boolean modifyMedWithout(MyMedicines myMedicines) {

        ContentValues values = new ContentValues();

        values.put(MED_TAKE, myMedicines.getMed_take());
        values.put(MED_FORM, myMedicines.getMed_form());
        values.put(MED_SUB_FORM, myMedicines.getMed_sub_form());
        values.put(MED_SUB_FORM2, myMedicines.getMed_sub_form2());
        values.put(MED_DOSE, myMedicines.getMed_dose());
        values.put(MED_IMAGE, myMedicines.getMed_image());
        values.put(MED_USER_ID, myMedicines.getMed_user_id());

        long upRes = db.update(TB_MY_MEDICINES, values, ID_MEDICINE + " = ?", new String[]{String.valueOf(myMedicines.getId_med())});

        if (upRes > 0) {
            return true;
        } else {
            return false;
        }


    }


}
