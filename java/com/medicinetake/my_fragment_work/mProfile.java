package com.medicinetake.my_fragment_work;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtPic;
import com.medicinetake.broadcast.AlarmBroadcast;
import com.medicinetake.database.MedUserDb;
import com.medicinetake.database.PicUserDb;
import com.medicinetake.database.ReminderDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.Pic;
import com.medicinetake.db_tables.User;
import com.medicinetake.interfaces.OnClickPic;
import com.medicinetake.users_activities.CreateUser;
import com.medicinetake.users_activities.ModifyUser;
import com.medicinetake.users_activities.UsersChoosing;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class mProfile extends Fragment {

    /**
     * XML declarations
     */

    private TextView tv_username, tv_gender, tv_age;
    private ImageView img_pic_user, img_edit_pic;
    private EditText et_username, et_age;
    private RadioButton radio_edit_f, radio_edit_m;
    private RecyclerView rec_pic_edit;
    private ImageButton imgBtn_edit, imgBtn_save_edit;
    private LinearLayout linear_info, linear_edit;

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
    private MedUserDb medDb;
    private ReminderDb reminderDb;

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_mm, container, false);

        /**call method**/
        initialisation();

        getUserInfo();

        /**editing user profile*/

        return view;
    }

    /**
     * Initialisations
     **/
    private void initialisation() {
        MmFragment mmFragment = (MmFragment) getActivity();
        id_user = mmFragment.getIdUser();

        //initialisation xml
        img_pic_user = view.findViewById(R.id.img_pic_pr);
        tv_username = view.findViewById(R.id.text_username_pr);
        tv_gender = view.findViewById(R.id.text_gender_pr);
        tv_age = view.findViewById(R.id.text_age_pr);

        userDb = new UserDb(getContext());
        medDb = new MedUserDb(getContext());
        reminderDb = new ReminderDb(getContext());


    }

    /****/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    /**
     * get user info from database
     **/

    private void getUserInfo() {
        userDb.OpenDb();
        User user = userDb.getUser(id_user);

        if (user.getUser_img() != null) {
            img_pic_user.setImageBitmap(BitmapFactory.decodeByteArray(user.getUser_img(), 0, user.getUser_img().length));

        }
        tv_username.setText(user.getUsername());
        tv_gender.setText(user.getUser_gender());
        tv_age.setText(user.getUser_age());

        userDb.CloseDb();
    }


    /**
     * the method can help you to put some action in the toolbar like a delete or save or something else
     **/

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.btn_pr_edit, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.it_delete:
                deleteUser();
                return true;

            case R.id.it_modify:
                Intent iModify = new Intent(getContext(), ModifyUser.class);
                iModify.putExtra("id user modify", id_user);
                startActivity(iModify);
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * delete user
     **/
    private void deleteUser() {

        AlertDialog.Builder bAlert = new AlertDialog.Builder(getContext());
        bAlert.setTitle("Delete User").setMessage("\n").setMessage("This action will be remove all your data!");

        bAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                MmFragment mmFragment = (MmFragment) getActivity();


                boolean resU, resM, resR;

                ArrayList<Integer> medList = new ArrayList<>();
                ArrayList<Integer> remList = new ArrayList<>();

                userDb.OpenDb();
                medDb.OpenDb();
                reminderDb.OpenDb();

                medList = medDb.getAllIdMedicine(id_user);
                for (int idM : medList) {
                    remList = reminderDb.getAllReminderId(idM);
                    for (int idR : remList) {
                        mmFragment.cancelAlarm(idR);
                    }
                    resR = reminderDb.deleteAllReminder(idM);
                    Toast.makeText(getContext(), "delete " + idM, Toast.LENGTH_SHORT).show();
                }

                resM = medDb.deleteAllMedByUser(id_user);
                resU = userDb.deleteUser(id_user);
                if (resU) {
                    Toast.makeText(getContext(), "deleted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), UsersChoosing.class);
                    startActivity(intent);
                    mmFragment.finish();
                } else {
                    Toast.makeText(getContext(), "not deleted", Toast.LENGTH_SHORT).show();
                }
                userDb.CloseDb();
                medDb.CloseDb();
                reminderDb.CloseDb();

            }
        });

        bAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        });


        AlertDialog alertD = bAlert.create();
        alertD.show();


    }

}



