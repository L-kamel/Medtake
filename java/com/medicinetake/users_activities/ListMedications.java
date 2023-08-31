package com.medicinetake.users_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtMedList;
import com.medicinetake.database.ListMedDb;
import com.medicinetake.db_tables.Categories;
import com.medicinetake.db_tables.ListMedicines;
import com.medicinetake.db_tables.MyMedicines;
import com.medicinetake.interfaces.OnClickListMed;

import java.util.ArrayList;

public class ListMedications extends AppCompatActivity {

    /****XML *****/
    private RecyclerView rec_list_med;
    private Toolbar toolbar;
    private EditText et_search;


    private RecAdtMedList adtMedList;
    private RecyclerView.LayoutManager layoutMedList;


    private ArrayList<ListMedicines> listMedicines;
    private ArrayList<Categories> categories;


    private int id_user;

    public static String TAG_ID_MED_LIST = "id med list";
    public static String TAG_ID_USER_LIST = "id user list";

    /***database*****/
    private ListMedDb listMedDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_medications);

        /***call methods************/
        initialisation();
        StatusBar();
        getToolbar();
        getListMedicines();


    }


    /***initialisations**********/

    private void initialisation() {
        rec_list_med = findViewById(R.id.rec_medicines_list);
        toolbar = findViewById(R.id.toolbar_listMedicines);
        //    et_search = findViewById(R.id.editText_search);

        listMedDb = new ListMedDb(this);

        Intent intent = getIntent();
        id_user = intent.getIntExtra("id user to medicine list", -1);

    }


    /**
     * toolbar
     **/
    private void StatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= 21) {

            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentAddMed3));

        }
    }

    /****get toolbar menu*******/


    private void getToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(" ");

    }


    /***search for medicines********/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_medicine, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search_medicine).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                listMedDb.OpenDb();
                ArrayList<ListMedicines> listMe = listMedDb.getSearchMedicines(newText);
                ArrayList<Categories> categ = listMedDb.searchCategories(newText);
                adtMedList = new RecAdtMedList(listMe, categ, new OnClickListMed() {
                    @Override
                    public void onClickListMed(int id_med_list) {
                        Intent i = new Intent(getBaseContext(), MoreInfo.class);
                        i.putExtra(TAG_ID_MED_LIST, id_med_list);
                        i.putExtra(TAG_ID_USER_LIST, id_user);
                        startActivity(i);
                    }
                });
                layoutMedList = new LinearLayoutManager(getBaseContext());
                listMedDb.CloseDb();

                rec_list_med.setAdapter(adtMedList);
                rec_list_med.setLayoutManager(layoutMedList);

                adtMedList.notifyDataSetChanged();
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
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
     * get medicines list
     *******/

    private void getListMedicines() {

        listMedDb.OpenDb();
        listMedicines = listMedDb.getListMeds();
        categories = listMedDb.getCategories();
        adtMedList = new RecAdtMedList(listMedicines, categories, new OnClickListMed() {

            @Override
            public void onClickListMed(int id_med_list) {

                Intent i = new Intent(getBaseContext(), MoreInfo.class);
                i.putExtra(TAG_ID_MED_LIST, id_med_list);
                i.putExtra(TAG_ID_USER_LIST, id_user);
                startActivity(i);

            }
        });
        layoutMedList = new LinearLayoutManager(this);
        listMedDb.CloseDb();

        rec_list_med.setAdapter(adtMedList);
        rec_list_med.setLayoutManager(layoutMedList);

    }

}