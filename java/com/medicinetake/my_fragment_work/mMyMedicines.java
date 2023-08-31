package com.medicinetake.my_fragment_work;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtMed;
import com.medicinetake.database.MedUserDb;
import com.medicinetake.db_tables.MyMedicines;
import com.medicinetake.interfaces.OnClickMed;
import com.medicinetake.users_activities.AddMedicines;
import com.medicinetake.users_activities.ReminderList;

import java.sql.Struct;
import java.util.ArrayList;

public class mMyMedicines extends Fragment {


    /***XML */

    private FloatingActionButton floating_add_med;
    private RecyclerView rec_my_med_list;


    /****/

    /**
     * variables for manging your data
     **/
    private int id_user;
    public static RecAdtMed adtMed;
    private RecyclerView.LayoutManager layoutMed;

    /*****/
    public static String SEND_USER_ID_TO_ADD_MED = "send user id to add med";
    public static String Id_MED_To_REM_LIST = "send id med to rem list";
    public static String Id_UsEr_To_REM_LIST = "send id user to rem list";


    private View view;


    /***
     * database
     *
     * */
    private MedUserDb medDb;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_medicines_mm, container, false);

        /**call methods**/
        initialisation();
        floatClick_addMed();
        getMyMedList();

        return view;
    }


    /**
     * initialisation
     **/
    private void initialisation() {
        MmFragment mmFragment = (MmFragment) getActivity();
        id_user = mmFragment.getIdUser();

        rec_my_med_list = view.findViewById(R.id.rec_my_medicines_list);
        floating_add_med = view.findViewById(R.id.floating_addMedicine);

        medDb = new MedUserDb(getContext());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    /**
     * go to add med by clicking on floating action button
     **/
    private void floatClick_addMed() {
        floating_add_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMedIntent();
            }
        });
    }

    public void addMedIntent() {
        Intent iAddMed = new Intent(getContext(), AddMedicines.class);
        iAddMed.putExtra(SEND_USER_ID_TO_ADD_MED, id_user);
        startActivity(iAddMed);
    }
    /*********************/


    /**
     * display my list of medicines
     ****/

    private void getMyMedList() {
        ArrayList<MyMedicines> myMedicines = new ArrayList<>();
        medDb.OpenDb();
        myMedicines = medDb.getAllMedicines(id_user);
        medDb.CloseDb();
        adtMed = new RecAdtMed(myMedicines, new OnClickMed() {
            @Override
            public void onClickMed(int id_med) {

                final Intent iRemList = new Intent(getContext(), ReminderList.class);
                iRemList.putExtra(Id_MED_To_REM_LIST, id_med);
                iRemList.putExtra(Id_UsEr_To_REM_LIST, id_user);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(iRemList);
                    }
                }, 450);
//                MmFragment mmFragment = (MmFragment) getActivity();
//                mmFragment.finish();
            }
        });


        layoutMed = new LinearLayoutManager(getContext());


        rec_my_med_list.setAdapter(adtMed);
        rec_my_med_list.setLayoutManager(layoutMed);


    }


}
