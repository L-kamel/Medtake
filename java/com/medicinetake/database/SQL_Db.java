package com.medicinetake.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL_Db{

    public SQLiteDatabase db;
    public SQLiteOpenHelper hp;

    public SQL_Db(Context context) {
        hp = new MedTakeDb(context);
    }

    public void OpenDb() {
        db = hp.getWritableDatabase();
    }

    public void CloseDb() {
        if (db != null) {
            db.close();
        }
    }

}
