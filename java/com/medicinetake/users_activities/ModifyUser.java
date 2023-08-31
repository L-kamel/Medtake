package com.medicinetake.users_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtPic;
import com.medicinetake.database.MedUserDb;
import com.medicinetake.database.PicUserDb;
import com.medicinetake.database.ReminderDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.Pic;
import com.medicinetake.db_tables.User;
import com.medicinetake.interfaces.OnClickPic;
import com.medicinetake.my_fragment_work.MmFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ModifyUser extends AppCompatActivity {

    /**
     * XML declarations
     */

    private ImageView img_edit_pic;
    private EditText et_username, et_age;
    private RadioButton radio_edit_f, radio_edit_m;
    private RecyclerView rec_pic_edit;
    private ImageButton imgBtn_save_edit;
    private Toolbar toolbar;

    /**
     * Variables
     */
    private int id_user;
    private RecAdtPic adtPic;
    private RecyclerView.LayoutManager layoutPic;

    /****/

    /**
     * database
     **/

    private String username_et, age_et, gender_et; ///////these are for updating user info


    private UserDb userDb;
    private PicUserDb picDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_user);


        initialisation();

        StatusBar();
        getToolbar();

        getInfoUser();
        getPicsRec();
        femaleMale();
        saveEditPr();

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
     * Initialisations
     **/
    private void initialisation() {

        Intent intent = getIntent();
        id_user = intent.getIntExtra("id user modify", -1);

        img_edit_pic = findViewById(R.id.edit_img_pic_pr);
        et_username = findViewById(R.id.edit_username_pr);
        et_age = findViewById(R.id.edit_age_pr);
        radio_edit_f = findViewById(R.id.edit_radio_female);
        radio_edit_m = findViewById(R.id.edit_radio_male);
        imgBtn_save_edit = findViewById(R.id.imgBtn_save_edit_pr);
        toolbar = findViewById(R.id.toolbar_modify_user);
        rec_pic_edit = findViewById(R.id.rec_pic_edit_pr);

        userDb = new UserDb(this);
        picDb = new PicUserDb(this);

    }


    /**
     * Toolbar and status bar options
     ***/

    private void StatusBar() {

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentAddMed2));
        }
    }


    private void getToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }


    /***get info user****/

    private void getInfoUser() {
        userDb.OpenDb();
        User user = userDb.getUser(id_user);

        if (user.getUser_img() != null) {
            img_edit_pic.setImageBitmap(BitmapFactory.decodeByteArray(user.getUser_img(), 0, user.getUser_img().length));
        }
        et_username.setText(user.getUsername());
        if (user.getUser_gender().equals("Male")) {
            radio_edit_m.setChecked(true);
            gender_et = "Male";
        }
        if (user.getUser_gender().equals("Female")) {
            radio_edit_f.setChecked(true);
            gender_et = "Female";
        }
        et_age.setText(user.getUser_age());

        userDb.CloseDb();
    }

    /**
     * getting gender
     **/
    private void femaleMale() {
        radio_edit_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radio_edit_f.setChecked(false);
                gender_et = "Male";
                Toast.makeText(getBaseContext(), "" + gender_et, Toast.LENGTH_SHORT).show();
            }
        });
        radio_edit_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radio_edit_m.setChecked(false);
                gender_et = "Female";
                Toast.makeText(getBaseContext(), "" + gender_et, Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * this for saving editUser profile
     ***/
    private void saveEditPr() {
        imgBtn_save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUser();
                Intent intent = new Intent(getBaseContext(), MmFragment.class);
                intent.putExtra("id user modify", id_user);
                startActivity(intent);
            }
        });
    }

    /**
     * Update user info
     **/
    boolean resEdit;
    byte[] img_pic;

    private void updateUser() {

        User user;
        username_et = et_username.getText().toString();
        age_et = et_age.getText().toString();
        img_pic = pic(img_edit_pic);
        if (img_pic == null) {
            userDb.OpenDb();
            User user1 = userDb.getUser(id_user);
            img_pic = user1.getUser_img();
            userDb.CloseDb();
        }
        if (et_username.length() == 0) {
            et_username.setError("!");
        } else if (et_age.length() == 0) {
            et_age.setError("!");
        } else if (gender_et == null) {
            Toast.makeText(getBaseContext(), "Gender!", Toast.LENGTH_SHORT).show();
        } else {

            user = new User(username_et, age_et, gender_et, img_pic);
            userDb.OpenDb();
            if (userDb.ifUsernameExist(username_et)) {
                resEdit = userDb.editUserWithoutName(id_user, user);
                if (resEdit) {
                    Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
                } else {

                }
            } else {
                resEdit = userDb.editUserInfo(id_user, user);
                if (resEdit) {
                    Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
                } else {

                }
            }
        }

        userDb.CloseDb();
    }

    /**
     * get pic for edit user
     **/

    private void getPicsRec() {
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

        layoutPic = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        rec_pic_edit.setAdapter(adtPic);
        rec_pic_edit.setLayoutManager(layoutPic);

    }

    /**
     * Method for getting pictures for user
     **/
    private String sImgPicEdit = "o";

    private void getPic(int id_pic) {
        picDb.OpenDb();
        Pic pic = picDb.getOnePic(id_pic);
        img_edit_pic.setImageBitmap(BitmapFactory.decodeByteArray(pic.getPic(), 0, pic.getPic().length));
        sImgPicEdit = "picDb says yes";
        picDb.CloseDb();
    }

    /**
     *
     **/

    private byte[] pic(ImageView img) {
        if (sImgPicEdit.equals("picDb says yes")) {
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