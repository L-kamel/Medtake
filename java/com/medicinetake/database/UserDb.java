package com.medicinetake.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.medicinetake.db_tables.User;

import java.util.ArrayList;

import static com.medicinetake.database.MedTakeDb.ID_USER;
import static com.medicinetake.database.MedTakeDb.TB_USERS;
import static com.medicinetake.database.MedTakeDb.USER_AGE;
import static com.medicinetake.database.MedTakeDb.USER_GENDER;
import static com.medicinetake.database.MedTakeDb.USER_IMG;
import static com.medicinetake.database.MedTakeDb.USER_NAME;

public class UserDb extends SQL_Db {

    public UserDb(Context context) {
        super(context);
    }

    /**
     * insert user info
     ****/
    public boolean insertInfoUser(User user) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getUsername());
        values.put(USER_AGE, user.getUser_age());
        values.put(USER_GENDER, user.getUser_gender());
        values.put(USER_IMG, user.getUser_img());
        long result = db.insert(TB_USERS, null, values);
        if (result != -1) {
            return true;
        } else {
            return false;
        }
    }

    /***
     *this for getting users list
     * ***/
    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        Cursor c = db.rawQuery("SELECT * FROM " + TB_USERS, null);

        if (c.moveToFirst()) {

            do {
                int idUser = c.getInt(c.getColumnIndex(ID_USER));
                String username = c.getString(c.getColumnIndex(USER_NAME));
                String user_age = c.getString(c.getColumnIndex(USER_AGE));
                String user_gender = c.getString(c.getColumnIndex(USER_GENDER));
                byte[] user_img = c.getBlob(c.getColumnIndex(USER_IMG));

                User user = new User(idUser, username, user_age, user_gender, user_img);

                users.add(user);
            } while (c.moveToNext());

            c.close();
        }

        return users;
    }

    /**
     * this action for getting one user
     **/

    public User getUser(int id) {

        Cursor c = db.rawQuery("SELECT * FROM " + TB_USERS + " WHERE " + ID_USER + " = ? ", new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {

            int idUser = c.getInt(c.getColumnIndex(ID_USER));
            String username = c.getString(c.getColumnIndex(USER_NAME));
            String user_age = c.getString(c.getColumnIndex(USER_AGE));
            String user_gender = c.getString(c.getColumnIndex(USER_GENDER));
            byte[] user_img = c.getBlob(c.getColumnIndex(USER_IMG));
            User user = new User(idUser, username, user_age, user_gender, user_img);
            c.close();

            return user;
        } else {
            return null;
        }
    }

    /**
     * this for getting user id by User class
     ***/

    public User getIdUser(User user) {

        Cursor c = db.rawQuery("SELECT * FROM " + TB_USERS +
                " WHERE " + USER_NAME + " = ? ", new String[]{user.getUsername()});

        if (c.moveToFirst()) {

            int idUser = c.getInt(c.getColumnIndex(ID_USER));
            String username = c.getString(c.getColumnIndex(USER_NAME));
            String user_age = c.getString(c.getColumnIndex(USER_AGE));
            String user_gender = c.getString(c.getColumnIndex(USER_GENDER));
            byte[] user_img = c.getBlob(c.getColumnIndex(USER_IMG));
            User user1 = new User(idUser, username, user_age, user_gender, user_img);
            c.close();

            return user1;
        } else {
            return null;
        }
    }


    /**
     * this is because of the user have a unique name
     ***/
    public boolean editUserWithoutName(int id_u, User user) {
        ContentValues values = new ContentValues();

        //    values.put(USER_NAME, user.getUsername());
        values.put(USER_AGE, user.getUser_age());
        values.put(USER_GENDER, user.getUser_gender());
        values.put(USER_IMG, user.getUser_img());

        long rEdit = db.update(TB_USERS, values, ID_USER + " = ? ", new String[]{String.valueOf(id_u)});

        if (rEdit > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for updating userInfo
     ***/

    public boolean editUserInfo(int id_u, User user) {
        ContentValues values = new ContentValues();

        values.put(USER_NAME, user.getUsername());
        values.put(USER_AGE, user.getUser_age());
        values.put(USER_GENDER, user.getUser_gender());
        values.put(USER_IMG, user.getUser_img());

        long rEdit = db.update(TB_USERS, values, ID_USER + " = ? ", new String[]{String.valueOf(id_u)});

        if (rEdit > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * username if exist
     **/

    public boolean ifUsernameExist(String sUsername) {
        Cursor c = db.rawQuery("SELECT * FROM " + TB_USERS +
                " WHERE " + USER_NAME + " LIKE ? ", new String[]{sUsername});
        if (c.moveToFirst()) {
            c.close();
            return true;
        } else {
            return false;
        }
    }

    /**
     * delete user profile
     **/
    public boolean deleteUser(int id) {
        int resD = db.delete(TB_USERS, ID_USER + " = ? ", new String[]{String.valueOf(id)});
        if (resD > 0) {
            return true;
        } else {
            return false;
        }
    }

}
