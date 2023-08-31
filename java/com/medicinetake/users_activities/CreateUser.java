package com.medicinetake.users_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtPic;
import com.medicinetake.database.PicUserDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.Pic;
import com.medicinetake.db_tables.User;
import com.medicinetake.interfaces.OnClickPic;
import com.medicinetake.my_fragment_work.MmFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CreateUser extends AppCompatActivity {
    /**
     * XML declarations
     ***/
    private Button save_crt_user;
    private EditText et_username, et_user_age;
    private RadioButton rd_female, rd_male;
    private RecyclerView rec_pic;
    private ImageView img_pic_user;
    private Toolbar toolbar;

    /**************/

    /**
     * getting object classes
     *****/
    private RecyclerView.LayoutManager layoutPic;
    private RecAdtPic adtPic;

    private UserDb userDb;
    private PicUserDb picDb;

    /**
     * Variables for saving into db;
     **/
    private String user_name, user_age, user_gender;
    /*******/

    /****
     *
     * some variables helping you to manage yor code smoothly
     * ***/

    private Animation animation;
    public static String sImgPic;

    /*******/

    /**
     * static variables
     ****/

    public static String SEND_ID_USER_FROM_CRT = "id user from create user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        /** Methods****/
        initialisation();
        StatusBar();
        getToolbar();
        gettingPics();
        getPic(1);
        femaleMale();
        saveDataUser();
    }

    /*** Initialisation ***/

    private void initialisation() {
        toolbar = findViewById(R.id.toolbar_crt_user);
        save_crt_user = findViewById(R.id.btn_save_create_user);
        et_username = findViewById(R.id.edit_username);
        et_user_age = findViewById(R.id.edit_user_age);
        rd_female = findViewById(R.id.radio_female);
        rd_male = findViewById(R.id.radio_male);
        img_pic_user = findViewById(R.id.img_user_pic);
        rec_pic = findViewById(R.id.recycler_user_pic);
        //call user database
        userDb = new UserDb(getBaseContext());
        //call pic database
        picDb = new PicUserDb(getBaseContext());

    }


    private void StatusBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorsta2));
        }
    }

    /**
     * toolbar
     ***/
    public void getToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * this action for getting pics for user from data
     *****/
    private void gettingPics() {
        ArrayList<Pic> pics = new ArrayList<>();
        picDb.OpenDb();
        pics = picDb.getPicUser();
        picDb.CloseDb();
        adtPic = new RecAdtPic(pics, new OnClickPic() {
            @Override
            public void onClickPic(int id_pic) {
                getPic(id_pic);
            }
        });
        layoutPic = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rec_pic.setAdapter(adtPic);
        rec_pic.setLayoutManager(layoutPic);

    }

    /**
     * this action for saving user data
     ***/
    private void saveDataUser() {
        save_crt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertInfoUser();
            }
        });
    }

    /**
     * radio button
     **/
    public void femaleMale() {
        rd_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rd_female.setChecked(false);
                user_gender = "Male";
                Toast.makeText(getBaseContext(), "" + user_gender, Toast.LENGTH_SHORT).show();
            }
        });

        rd_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rd_male.setChecked(false);
                user_gender = "Female";
                Toast.makeText(getBaseContext(), "" + user_gender, Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * this method for insert user data into database
     *****/

    private void insertInfoUser() {
        boolean res;
        animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.anim_edit_text);
        user_name = et_username.getText().toString();
        user_age = et_user_age.getText().toString();
        byte[] pic_user = pic(img_pic_user);
        if (et_username.length() == 0) {
            et_username.startAnimation(animation);
            et_username.setError("!");
        } else if (et_user_age.length() == 0) {
            et_user_age.startAnimation(animation);
            et_user_age.setError("!");
        } else if (user_gender == null) {
            Toast.makeText(this, "Set your Gender", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(user_name, user_age, user_gender, pic_user);
            userDb.OpenDb();
            res = userDb.insertInfoUser(user);
            User user1 = userDb.getIdUser(user);
            if (res) {
                Toast.makeText(this, "W e l c o m e", Toast.LENGTH_SHORT).show();
                Intent iSaveCrt = new Intent(getBaseContext(), MmFragment.class);
                iSaveCrt.putExtra(SEND_ID_USER_FROM_CRT, user1.getId_user());
                startActivity(iSaveCrt);
                finish();
                et_username.setText("");
                et_user_age.setText("");
                rd_female.setChecked(false);
                rd_male.setChecked(false);
            } else {
                et_username.setError("Name exist !");
            }
            userDb.CloseDb();
        }
    }


    /**
     * Method for getting pictures for user
     **/

    private void getPic(int id_pic) {
        picDb.OpenDb();
        Pic pic = picDb.getOnePic(id_pic);
        img_pic_user.setImageBitmap(BitmapFactory.decodeByteArray(pic.getPic(), 0, pic.getPic().length));
        sImgPic = "picDb says yes";
        picDb.CloseDb();
    }

    /****
     * This Method helps you to save image into database
     *
     * */
    private byte[] pic(ImageView img) {
        if (sImgPic.equals("picDb says yes")) {
            Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] bytes = outputStream.toByteArray();

            return bytes;
        } else {
            return null;
        }
    }

}