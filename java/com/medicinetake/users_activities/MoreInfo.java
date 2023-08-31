package com.medicinetake.users_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medicinetake.R;
import com.medicinetake.database.ListMedDb;
import com.medicinetake.db_tables.Categories;
import com.medicinetake.db_tables.ListMedicines;

import static com.medicinetake.users_activities.ListMedications.TAG_ID_MED_LIST;

public class MoreInfo extends AppCompatActivity {

    /**
     * XML
     *******/

    private Toolbar toolbar;

    private TextView tv_med_name, tv_med_reference, tv_category, tv_form, tv_dose, tv_indication, tv_dosage;
    private ImageView img_med;

    private LinearLayout linearLayout_dose, linearLayout_dosage;

    /***databases****/
    private ListMedDb listMedDb;

    /****variables to manage your data*******/

    private int id_user, id_med_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        getIdIntent();
        initialisation();
        StatusBar();
        getToolbar();
        displayMedicineInfo();
    }

    /***initialisation********/
    private void initialisation() {

        toolbar = findViewById(R.id.toolbar_more_info);

        tv_med_name = findViewById(R.id.text_info_med_name);
        tv_med_reference = findViewById(R.id.text_info_reference);
        img_med = findViewById(R.id.img_info_med_image);
        tv_category = findViewById(R.id.text_info_category);
        tv_form = findViewById(R.id.text_info_form);
        tv_dose = findViewById(R.id.text_info_dose);
        tv_indication = findViewById(R.id.text_info_indication);
        tv_dosage = findViewById(R.id.text_info_dosage);

        linearLayout_dosage = findViewById(R.id.linear_dosage);
        linearLayout_dose = findViewById(R.id.linear_dose);


        listMedDb = new ListMedDb(this);

    }


    /**
     * Toolbar and status bar options
     ***/

    private void StatusBar() {

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentAddMed3));
        }
    }


    private void getToolbar() {
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

    /******get id of user & id of med by getIntent()****/
    private void getIdIntent() {
        Intent intent = getIntent();
        id_med_list = intent.getIntExtra(TAG_ID_MED_LIST, -1);
    }


    /***get medicines info by the id_med_list from ListMedicines****/

    private void displayMedicineInfo() {

        listMedDb.OpenDb();
        ListMedicines listMed = listMedDb.getMedsInfo(id_med_list);
        Categories category = listMedDb.getCategoryInfo(listMed.getList_id_category());
        listMedDb.CloseDb();

        tv_med_name.setText(listMed.getList_med_name());
        if (listMed.getList_img_med() != null) {
            img_med.setImageBitmap(BitmapFactory.decodeByteArray(listMed.getList_img_med(), 0, listMed.getList_img_med().length));
        }
        tv_med_reference.setText(listMed.getList_reference());
        tv_category.setText(category.getCateg_name());
        tv_form.setText(listMed.getList_form());
        if (!listMed.getList_dose().equals("00")) {
            linearLayout_dose.setVisibility(View.VISIBLE);
            tv_dose.setText(listMed.getList_dose());
        }

        tv_indication.setText(listMed.getList_indication());

        if (!listMed.getList_dosage().equals("00")) {
            linearLayout_dose.setVisibility(View.VISIBLE);
            tv_dosage.setText(listMed.getList_dosage());
        }


    }


}