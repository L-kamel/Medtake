package com.medicinetake.users_activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtHistoryTook;
import com.medicinetake.database.HistoryDb;
import com.medicinetake.database.MedUserDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.Histories;
import com.medicinetake.db_tables.MyMedicines;

import java.util.ArrayList;

public class TookFragment extends Fragment {

    /***XML******/

    private RecyclerView recyclerView_taken;

    private View view;

    private UserDb userDb;
    private HistoryDb historyDb;

    private RecAdtHistoryTook adtHistoryTook;
    private RecyclerView.LayoutManager layout_history;


    private int id_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.took_fragment, container, false);

        initialisation();
        MedicinesTaken();

        return view;
    }

    /**
     *
     * initialisation
     *
     * */
    private void initialisation() {

        recyclerView_taken = view.findViewById(R.id.rec_history_med_took);
        historyDb = new HistoryDb(getContext());

    }

    /*****get the medication taken****/

    private void MedicinesTaken() {

        History history = (History) getActivity();
        id_user = history.getIdUser();

        historyDb.OpenDb();


        ArrayList<Histories> myMedicines = historyDb.getMedicinesTaken(id_user,"take");

        adtHistoryTook = new RecAdtHistoryTook(myMedicines);
        layout_history = new LinearLayoutManager(getContext());

        historyDb.CloseDb();

        recyclerView_taken.setAdapter(adtHistoryTook);
        recyclerView_taken.setLayoutManager(layout_history);

    }


}
