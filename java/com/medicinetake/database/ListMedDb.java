package com.medicinetake.database;

import android.content.Context;
import android.database.Cursor;

import com.medicinetake.db_tables.Categories;
import com.medicinetake.db_tables.ListMedicines;

import java.io.StringReader;
import java.util.ArrayList;

import static com.medicinetake.database.MedTakeDb.CATEGORY_NAME;
import static com.medicinetake.database.MedTakeDb.ID_CATEGORY;
import static com.medicinetake.database.MedTakeDb.ID_MED_LIST;
import static com.medicinetake.database.MedTakeDb.LIST_DOSAGE;
import static com.medicinetake.database.MedTakeDb.LIST_DOSE;
import static com.medicinetake.database.MedTakeDb.LIST_FORM;
import static com.medicinetake.database.MedTakeDb.LIST_ID_CATEGORY;
import static com.medicinetake.database.MedTakeDb.LIST_IMAGE_MED;
import static com.medicinetake.database.MedTakeDb.LIST_INDICATION;
import static com.medicinetake.database.MedTakeDb.LIST_MED_NAME;
import static com.medicinetake.database.MedTakeDb.LIST_REFERENCE;
import static com.medicinetake.database.MedTakeDb.TB_CATEGORIES;
import static com.medicinetake.database.MedTakeDb.TB_MEDICINES_LIST;

public class ListMedDb extends SQL_Db {
    public ListMedDb(Context context) {
        super(context);
    }

    /***getting medicines list*******/

    public ArrayList<ListMedicines> getListMeds() {

        ArrayList<ListMedicines> listMedicines = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_MEDICINES_LIST, null);

        if (c.moveToFirst()) {
            do {

                int id_list_med = c.getInt(c.getColumnIndex(ID_MED_LIST));
                String list_med_name = c.getString(c.getColumnIndex(LIST_MED_NAME));
                String list_reference = c.getString(c.getColumnIndex(LIST_REFERENCE));
                String list_form = c.getString(c.getColumnIndex(LIST_FORM));
                String list_dose = c.getString(c.getColumnIndex(LIST_DOSE));
                String list_indication = c.getString(c.getColumnIndex(LIST_INDICATION));
                String list_dosage = c.getString(c.getColumnIndex(LIST_DOSAGE));
                byte[] list_image_med = c.getBlob(c.getColumnIndex(LIST_IMAGE_MED));
                int list_id_categ = c.getInt(c.getColumnIndex(LIST_ID_CATEGORY));

                ListMedicines listMedicines1 = new ListMedicines(id_list_med, list_med_name, list_reference, list_form,
                        list_dose, list_indication, list_dosage, list_image_med, list_id_categ);
                listMedicines.add(listMedicines1);

            } while (c.moveToNext());

            c.close();
        }
        return listMedicines;
    }


    /**
     * get Categories
     ****/

    public ArrayList<Categories> getCategories() {
        ArrayList<Categories> categories = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_CATEGORIES + "" +
                " INNER JOIN " + TB_MEDICINES_LIST + " ON " + ID_CATEGORY + " = " + LIST_ID_CATEGORY, null);

        if (c.moveToFirst()) {
            do {
                int idC = c.getInt(c.getColumnIndex(ID_CATEGORY));
                String categ = c.getString(c.getColumnIndex(CATEGORY_NAME));

                Categories categories1 = new Categories(idC, categ);
                categories.add(categories1);
            } while (c.moveToNext());

            c.close();
        }
        return categories;

    }

    /****search  for medicines*****/
    public ArrayList<ListMedicines> getSearchMedicines(String medicine) {

        ArrayList<ListMedicines> listMedicines = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_MEDICINES_LIST +
                " WHERE " + LIST_REFERENCE + " LIKE ? ", new String[]{medicine + "%"});

        if (c.moveToFirst()) {
            do {
                int id_list_med = c.getInt(c.getColumnIndex(ID_MED_LIST));
                String list_med_name = c.getString(c.getColumnIndex(LIST_MED_NAME));
                String list_reference = c.getString(c.getColumnIndex(LIST_REFERENCE));
                String list_form = c.getString(c.getColumnIndex(LIST_FORM));
                String list_dose = c.getString(c.getColumnIndex(LIST_DOSE));
                String list_indication = c.getString(c.getColumnIndex(LIST_INDICATION));
                String list_dosage = c.getString(c.getColumnIndex(LIST_DOSAGE));
                byte[] list_image_med = c.getBlob(c.getColumnIndex(LIST_IMAGE_MED));
                int list_id_categ = c.getInt(c.getColumnIndex(LIST_ID_CATEGORY));
                ListMedicines listMedicines1 = new ListMedicines(id_list_med, list_med_name, list_reference, list_form,
                        list_dose, list_indication, list_dosage, list_image_med, list_id_categ);
                listMedicines.add(listMedicines1);

            } while (c.moveToNext());
            c.close();
        }

        return listMedicines;
    }


    /**
     * search categories
     ***/

    public ArrayList<Categories> searchCategories(String medName) {
        ArrayList<Categories> categories = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_CATEGORIES + "" +
                " INNER JOIN " + TB_MEDICINES_LIST + " ON " + ID_CATEGORY + " = " + LIST_ID_CATEGORY +
                " WHERE " + LIST_REFERENCE + " LIKE ?", new String[]{medName + "%"});

        if (c.moveToFirst()) {
            do {
                int idC = c.getInt(c.getColumnIndex(ID_CATEGORY));
                String categ = c.getString(c.getColumnIndex(CATEGORY_NAME));

                Categories categories1 = new Categories(idC, categ);
                categories.add(categories1);
            } while (c.moveToNext());

            c.close();
        }
        return categories;

    }

    /***getting med info by id *******/

    public ListMedicines getMedsInfo(int id_med) {

        Cursor c = db.rawQuery("SELECT * FROM " + TB_MEDICINES_LIST + " WHERE " + ID_MED_LIST + " = ? ",
                new String[]{String.valueOf(id_med)});

        if (c.moveToFirst()) {


            int id_list_med = c.getInt(c.getColumnIndex(ID_MED_LIST));
            String list_med_name = c.getString(c.getColumnIndex(LIST_MED_NAME));
            String list_reference = c.getString(c.getColumnIndex(LIST_REFERENCE));
            String list_form = c.getString(c.getColumnIndex(LIST_FORM));
            String list_dose = c.getString(c.getColumnIndex(LIST_DOSE));
            String list_indication = c.getString(c.getColumnIndex(LIST_INDICATION));
            String list_dosage = c.getString(c.getColumnIndex(LIST_DOSAGE));
            byte[] list_image_med = c.getBlob(c.getColumnIndex(LIST_IMAGE_MED));
            int list_id_categ = c.getInt(c.getColumnIndex(LIST_ID_CATEGORY));

            ListMedicines listMedicines1 = new ListMedicines(id_list_med, list_med_name, list_reference, list_form,
                    list_dose, list_indication, list_dosage, list_image_med, list_id_categ);

            c.close();

            return listMedicines1;
        } else {

            return null;
        }
    }

    /***getting category by id for med info ********/

    public Categories getCategoryInfo(int id_categ) {
        Cursor c = db.rawQuery("SELECT * FROM " + TB_CATEGORIES + " WHERE " + ID_CATEGORY + " = ? ",
                new String[]{String.valueOf(id_categ)});

        if (c.moveToFirst()) {
            int idC = c.getInt(c.getColumnIndex(ID_CATEGORY));
            String categ = c.getString(c.getColumnIndex(CATEGORY_NAME));
            Categories categories1 = new Categories(idC, categ);
            c.close();
            return categories1;
        } else {
            return null;

        }

    }

}
