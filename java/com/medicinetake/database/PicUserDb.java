package com.medicinetake.database;

import android.content.Context;
import android.database.Cursor;

import com.medicinetake.db_tables.Pic;

import java.util.ArrayList;

import static com.medicinetake.database.MedTakeDb.ID_PIC_USER;
import static com.medicinetake.database.MedTakeDb.PIC;
import static com.medicinetake.database.MedTakeDb.TB_PIC_USER;

public class PicUserDb extends SQL_Db {

    public PicUserDb(Context context) {
        super(context);
    }


    //getting all pictures
    public ArrayList<Pic> getPicUser() {
        ArrayList<Pic> pics = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_PIC_USER, null);
        if (c.moveToFirst()) {
            do {
                int id_pic = c.getInt(c.getColumnIndex(ID_PIC_USER));
                byte[] pic = c.getBlob(c.getColumnIndex(PIC));
                Pic pic1 = new Pic(id_pic, pic);
                pics.add(pic1);

            } while (c.moveToNext());

            c.close();
        }
        return pics;

    }

    //get only one pictures

    public Pic getOnePic(int id_p) {
        Cursor c = db.rawQuery("SELECT * FROM " + TB_PIC_USER + " WHERE " + ID_PIC_USER + " = ? ",
                new String[]{String.valueOf(id_p)});

        if (c.moveToFirst()) {

            int id_pic = c.getInt(c.getColumnIndex(ID_PIC_USER));
            byte[] pic = c.getBlob(c.getColumnIndex(PIC));
            Pic pic1 = new Pic(id_pic, pic);

            c.close();
            return pic1;
        } else {
            return null;
        }


    }


}
