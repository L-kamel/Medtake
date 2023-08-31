package com.medicinetake.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MedTakeDb extends SQLiteAssetHelper {

    //Database name
    public static final String Db = "MediTakeDb.db";
    //Application version
    public static final int App_v = 1;

    /***** Tables name*********************/

    //MyMedications Table
    public static final String TB_MY_MEDICINES = "My_Medicines";
    //Columns
    public static final String ID_MEDICINE = "id_medicine";
    public static final String MED_NAME = "med_name";
    public static final String MED_TAKE = "med_take";
    public static final String MED_FORM = "med_form";
    public static final String MED_SUB_FORM = "med_sub_form";
    public static final String MED_SUB_FORM2 = "med_sub_form2";
    public static final String MED_DOSE = "med_dose";
    public static final String MED_IMAGE = "med_image";
    public static final String MED_USER_ID = "med_user_id";


    //Users Table
    public static final String TB_USERS = "Users";
    //Columns
    public static final String ID_USER = "id_user";
    public static final String USER_NAME = "user_name";
    public static final String USER_AGE = "user_age";
    public static final String USER_GENDER = "user_gender";
    public static final String USER_IMG = "user_img";

    //pic user table
    public static final String TB_PIC_USER = "Pic_user";

    public static final String ID_PIC_USER = "id_pic_user";
    public static final String PIC = "pic";


    //image medicine table
    public static final String TB_IMAGE_MEDICINES = "Images_medicines";

    public static final String ID_IMAGE_MED = "id_image_med";
    public static final String IMAGE_MEDICINE = "image_med";


    //reminder table

    public static String TB_REMINDER = "Reminder";


    public static String ID_REMINDER = "id_reminder";
    public static String TIME = "time";
    public static String DATE = "date";
    public static String REPEAT_MINUTE = "repeat_min";
    public static String REPEAT_DAY = "repeat_day";
    public static String ACTIVE = "active";
    public static String ACTIVE_REP = "active_r";
    public static String ID_MED_REM = "id_med_rem";


    // Take medicine table

    public static String TB_MEDICINES_LIST = "Medicines_List";

    public static String ID_MED_LIST = "id_med_list";
    public static String LIST_MED_NAME = "list_med_name";
    public static String LIST_REFERENCE = "list_reference";
    public static String LIST_FORM = "list_form";
    public static String LIST_DOSE = "list_dose";
    public static String LIST_INDICATION = "list_indication";
    public static String LIST_DOSAGE = "list_dosage";
    public static String LIST_IMAGE_MED = "list_img_med";
    public static String LIST_ID_CATEGORY = "list_id_category";

    //Categories table
    public static String TB_CATEGORIES = "Categories";

    public static String ID_CATEGORY = "id_categ";
    public static String CATEGORY_NAME = "categ_name";


    //History table
    public static String TB_HISTORY = "History";

    public static String ID_HISTORY = "id_history";
    public static String MED_NAME_HISTORY = "med_name_history";
    public static String TIME_HISTORY = "time_history";
    public static String DATE_HISTORY = "date_history";
    public static String TAKE_HISTORY = "take";
    public static String ID_MED_HISTORY = "id_med_history";
    public static String ID_USER_HISTORY = "id_user_history";

    public MedTakeDb(Context context) {
        super(context, Db, null, App_v);
    }

}
