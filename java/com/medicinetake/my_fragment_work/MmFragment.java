package com.medicinetake.my_fragment_work;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.medicinetake.R;
import com.medicinetake.broadcast.AlarmBroadcast;
import com.medicinetake.database.PicUserDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.User;
import com.medicinetake.users_activities.AddMedicines;
import com.medicinetake.users_activities.CreateUser;
import com.medicinetake.users_activities.History;
import com.medicinetake.users_activities.ListMedications;
import com.medicinetake.users_activities.UsersChoosing;

public class MmFragment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * XML declarations
     ***/

    private Toolbar toolbar_mm;
    private NavigationView nav_left_view;
    private DrawerLayout drawerLayout_left;
    private BottomNavigationView nav_btm_view;
    private MeowBottomNavigation meowBottom;


    /*** database variables *****/

    private UserDb userDb;
    private PicUserDb picDb;

    /**
     * Variables for manage your code
     **/

    private int id_user, id_from_users_list, id_from_crt_user, id_user_f_rem_list, id_user_f_rem_list_for_rec, id_user_f_modify_user;


    /***
     * static variables
     * **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mm_fragment);

        /**call methods**/

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mHome()).commit();
        }

        initialisation();
        getIntents();
        getToolBarInit();
        // gettingFragmentBtmWork();
        gettingBottomMeo();

    }

    /*** Initialisation ***/

    private void initialisation() {

        toolbar_mm = findViewById(R.id.toolbar_mm_fragment);
        nav_left_view = findViewById(R.id.nav_left);
        drawerLayout_left = findViewById(R.id.drawer_left);
        nav_left_view.setNavigationItemSelectedListener(this);
        //nav_btm_view = findViewById(R.id.nav_btm);
        meowBottom = findViewById(R.id.bottom_nav);
        userDb = new UserDb(getBaseContext());
        picDb = new PicUserDb(getBaseContext());

    }

    /***this for specify the id of user with getIntent method**/

    private void getIntents() {
        Intent i = getIntent();
        id_from_users_list = i.getIntExtra(UsersChoosing.SEND_USER_ID, -1);
        id_from_crt_user = i.getIntExtra(CreateUser.SEND_ID_USER_FROM_CRT, -1);
        id_user_f_rem_list = i.getIntExtra("id user go home", -1);
        id_user_f_rem_list_for_rec = i.getIntExtra("id user for refreshing recyclerView", -1);
        id_user_f_modify_user = i.getIntExtra("id user modify", -1);

        if (id_from_users_list != -1) {
            id_user = id_from_users_list;
        }
        if (id_from_crt_user != -1) {
            id_user = id_from_crt_user;
        }
        if (id_user_f_rem_list != -1) {
            id_user = id_user_f_rem_list;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mHome()).commit();
        }
        if (id_user_f_rem_list_for_rec != -1) {
            id_user = id_user_f_rem_list_for_rec;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mMyMedicines()).commit();
        }
        if (id_user_f_modify_user != -1) {
            id_user = id_user_f_modify_user;
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mProfile()).commit();
        }

    }

    public int getIdUser() {
        return id_user;
    }

    /**
     * Toolbar initialisation
     *******/


    private void getToolBarInit() {
        setSupportActionBar(toolbar_mm);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout_left, toolbar_mm, R.string.Open_Drawer, R.string.Close_Drawer);
        drawerLayout_left.addDrawerListener(toggle);
        toggle.syncState();
        userDb.OpenDb();
        User user = userDb.getUser(id_user);
        toolbar_mm.setTitle(user.getUsername());

        View v = nav_left_view.getHeaderView(0);
        ImageView imgHeader = v.findViewById(R.id.img_header);
        TextView tv_username = v.findViewById(R.id.text_username_header);

        if (user.getUser_img() != null) {
            imgHeader.setImageBitmap(BitmapFactory.decodeByteArray(user.getUser_img(), 0, user.getUser_img().length));
        }

        tv_username.setText(user.getUsername());
        userDb.CloseDb();

    }


    /**
     * this method for getting frame (home , list Med) &
     * <p>
     * frame (add med) do another action going to another activity (Add Medicines)
     ***/

    private void gettingFragmentBtmWork() {

        nav_btm_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.btm_item_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mHome()).commit();
                        break;
                    case R.id.btm_item_add_med:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mMyMedicines()).commit();
                        //   addMedIntent();
                        break;
                }
                return true;
            }
        });
    }

    /***this methods for getting frame from nav drawer left ***/
    @Override
    public void onBackPressed() {
        if (drawerLayout_left.isDrawerOpen(GravityCompat.START)) {
            drawerLayout_left.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.left_item_switch_user:
                Intent intent = new Intent(getBaseContext(), UsersChoosing.class);
                startActivity(intent);
                break;
            case R.id.left_item_my_medicines:
                //   getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mMyMedicines()).commit();
                Intent iAddMed = new Intent(getBaseContext(), AddMedicines.class);
                iAddMed.putExtra("AddMedicines Id", id_user);
                startActivity(iAddMed);
                break;
            case R.id.left_item_history:
                Intent iH = new Intent(getBaseContext(), History.class);
                iH.putExtra("History Id", id_user);
                startActivity(iH);

                break;
            case R.id.left_item_crt_user:
                Intent iCrt = new Intent(getBaseContext(), CreateUser.class);
                startActivity(iCrt);
                break;

            case R.id.left_item_med_list:
                Intent iList = new Intent(getBaseContext(), ListMedications.class);
                iList.putExtra("id user to medicine list", id_user);
                startActivity(iList);
                break;

            case R.id.exit_app:
                System.exit(1);
                break;

        }
        drawerLayout_left.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * this method for getting frame (home , list Med) & (add med)
     ***/

    public void gettingBottomMeo() {

        meowBottom.add(new MeowBottomNavigation.Model(777, R.drawable.ic_homer_icon));
        meowBottom.add(new MeowBottomNavigation.Model(776, R.drawable.ic_user_2));
        meowBottom.add(new MeowBottomNavigation.Model(778, R.drawable.ic_pills_1));


        meowBottom.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });


        meowBottom.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case 777:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mHome()).commit();
                        break;
                    case 776:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mProfile()).commit();
                        break;
                    case 778:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mMyMedicines()).commit();
                        //   addMedIntent();
                        break;
                }
            }
        });


        meowBottom.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case 777:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mHome()).commit();
                        break;
                    case 776:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mProfile()).commit();
                        break;
                    case 778:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_mm, new mMyMedicines()).commit();
                        //   addMedIntent();
                        break;
                }
            }
        });
    }


    public void cancelAlarm(int id_med) {

        AlarmManager aManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent iBroadcast = new Intent(getBaseContext(), AlarmBroadcast.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), id_med, iBroadcast, PendingIntent.FLAG_CANCEL_CURRENT);
        aManager.cancel(pIntent);
        Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();

    }

    public void fin() {
        finish();
    }


}