package com.medicinetake.database;

import android.content.Context;
import android.database.Cursor;
import android.widget.ArrayAdapter;

import com.medicinetake.db_tables.ImageMed;

import java.util.ArrayList;

import static android.provider.MediaStore.Images.Thumbnails.IMAGE_ID;
import static com.medicinetake.database.MedTakeDb.ID_IMAGE_MED;
import static com.medicinetake.database.MedTakeDb.IMAGE_MEDICINE;
import static com.medicinetake.database.MedTakeDb.TB_IMAGE_MEDICINES;

public class ImageMedDb extends SQL_Db {
    public ImageMedDb(Context context) {
        super(context);
    }

    /**
     * get images for medicines
     */
    public ArrayList<ImageMed> getImagesMed() {
        ArrayList<ImageMed> images = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_IMAGE_MEDICINES, null);

        if (c.moveToFirst()) {
            do {
                int id_img = c.getInt(c.getColumnIndex(ID_IMAGE_MED));
                byte[] img_med = c.getBlob(c.getColumnIndex(IMAGE_MEDICINE));

                ImageMed imageMed = new ImageMed(id_img, img_med);
                images.add(imageMed);
            } while (c.moveToNext());

            c.close();
        }
        return images;
    }


    /**
     * get one image for medicine
     */
    public ImageMed getImage(int id_img) {

        Cursor c = db.rawQuery("SELECT * FROM " + TB_IMAGE_MEDICINES + " WHERE " + ID_IMAGE_MED + " = ?",
                new String[]{String.valueOf(id_img)});

        if (c.moveToFirst()) {
            int id_imgM = c.getInt(c.getColumnIndex(ID_IMAGE_MED));
            byte[] img_med = c.getBlob(c.getColumnIndex(IMAGE_MEDICINE));

            ImageMed imageMed = new ImageMed(id_imgM, img_med);

            c.close();

            return imageMed;
        } else {
            return null;
        }

    }


}
