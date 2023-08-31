package com.medicinetake.users_activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtUser;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.User;
import com.medicinetake.interfaces.OnClickUser;
import com.medicinetake.my_fragment_work.MmFragment;

import java.util.ArrayList;

public class UsersChoosing extends AppCompatActivity {


    /***
     *
     * XML declarations
     *
     * ***/

    private Button crt_user;
    private RecyclerView rec_list_users;

    /**************/

    /**
     * getting database
     ***/

    private UserDb userDb;


    /**
     * Variables for manage your code
     */
    private RecAdtUser adtUser;
    private RecyclerView.LayoutManager layoutUser;

    /*******/


    /**
     * static Variables
     ***/

    public static String SEND_USER_ID = "this user id from users list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_choosing);

        /** call Methods ***/
        Initialisation();
        StatusBar();
        createUser();
        chsUser();

    }

    /**
     * initialisation
     ***/

    private void Initialisation() {
        crt_user = findViewById(R.id.btn_create_user);
        rec_list_users = findViewById(R.id.users_list_rec);
        userDb = new UserDb(getBaseContext());
    }


    private void StatusBar() {

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentAddMed2));

        }
    }

    /**
     * this action for going to UsersChoosing class to create new user
     ***/

    private void createUser() {
        crt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iCrtUser = new Intent(getBaseContext(), CreateUser.class);
                startActivity(iCrtUser);
            }
        });
    }

    /**
     * this for choosing user by clicking on item from recyclerView
     * with getting position (id user)of user
     ***/

    public void chsUser() {

        ArrayList<User> users = new ArrayList<>();
        userDb.OpenDb();
        users = userDb.getUsers();
        userDb.CloseDb();

        adtUser = new RecAdtUser(users, new OnClickUser() {
            @Override
            public void onClickPicUser(final int id_user) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final Intent in = new Intent(getBaseContext(), MmFragment.class);
                        in.putExtra(SEND_USER_ID, id_user);
                        startActivity(in);
                        finish();
                    }
                }, 450);


            }
        });

        layoutUser = new LinearLayoutManager(this);
        rec_list_users.setAdapter(adtUser);
        rec_list_users.setLayoutManager(layoutUser);

    }


}