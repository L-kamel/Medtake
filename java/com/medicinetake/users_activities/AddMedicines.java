package com.medicinetake.users_activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.medicinetake.R;
import com.medicinetake.adapters_recycler.RecAdtImg;
import com.medicinetake.database.ImageMedDb;
import com.medicinetake.database.MedUserDb;
import com.medicinetake.database.UserDb;
import com.medicinetake.db_tables.ImageMed;
import com.medicinetake.db_tables.MyMedicines;
import com.medicinetake.db_tables.Pic;
import com.medicinetake.db_tables.User;
import com.medicinetake.interfaces.OnClick_imgMed;

import java.io.ByteArrayOutputStream;
import java.text.BreakIterator;
import java.util.ArrayList;

import static com.medicinetake.my_fragment_work.mMyMedicines.SEND_USER_ID_TO_ADD_MED;

public class AddMedicines extends AppCompatActivity {

    /***
     * XML Declaration
     * ***/

    private AppCompatSpinner spinner_take, spinner_mForm, spinner_subForm1, spinner_subForm2;
    private TextView tv_sub_form1, tv_sub_form2, tv_dose;
    private CardView cardView_subForm2, card_subForm2, card_img2;
    private Toolbar toolbar;
    private RecyclerView rec_img_med;
    private ImageView img_med;
    private Button btn_save;

    private EditText et_med_name, et_med_dose;

    /******/

    /***
     * variables for managing the code
     * **/


    public static String ID_MED_TO_REMINDER = "going to add reminder";
    public static String ID_USER_TO_REMINDER = "going to add with user id reminder";

    private String sImg = "0";

    private ArrayAdapter<CharSequence> charSp_med_take, charSp_med_form, charSp_sub_form1, charSp_sub_form2;
    private RecAdtImg adtImg;
    private RecyclerView.LayoutManager layoutImg;

    /***
     * database
     * ***/
    private UserDb userDb;
    private ImageMedDb imageMedDb;
    private MedUserDb medDb;

    /****variables for save on database ******/

    private String med_name, med_take, med_form, med_sub_form, med_sub_form2, med_dose;
    private byte[] med_img;
    private int id_user, med_user_id_f_dwr, med_user_id_f_btm, med_user_id_f_rem_list, med_med_id_f_rem_list, med_med_id_f_rem_list_modify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicines);

        /**call methods***/
        StatusBar();
        initialisation();
        gettingIntent();
        getImgMed();
        getToolbar();
        getMedTake();
        getMedicineForm();
        btnSaveMedicine();

//        if (med_med_id_f_rem_list != -1) {
//            getInfoForModify();
//            btn_save.setText("Modify");
//        }

    }

    /**
     * Initialisations
     ***/
    private void initialisation() {
        toolbar = findViewById(R.id.toolbar_add_medicine);

        et_med_name = findViewById(R.id.edit_medicine_name);
        spinner_take = findViewById(R.id.spinner_med_take);
        spinner_mForm = findViewById(R.id.spinner_med_from);
        spinner_subForm1 = findViewById(R.id.spinner_sub_from1);
        spinner_subForm2 = findViewById(R.id.spinner_sub_from2);
        et_med_dose = findViewById(R.id.edit_dose);
        cardView_subForm2 = findViewById(R.id.card_spinner_sub_from2);
        card_subForm2 = findViewById(R.id.card_sub_from2);
        card_img2 = findViewById(R.id.card_img_2);
        tv_sub_form1 = findViewById(R.id.t_2);
        tv_sub_form2 = findViewById(R.id.t_3);
        tv_dose = findViewById(R.id.t_5_dose);
        img_med = findViewById(R.id.img_addMed);
        rec_img_med = findViewById(R.id.rec_img_med);
        btn_save = findViewById(R.id.btn_save_med);


        userDb = new UserDb(getBaseContext());
        imageMedDb = new ImageMedDb(getBaseContext());
        medDb = new MedUserDb(getBaseContext());


    }


    /**
     * get data to save
     **/

    private void gettingIntent() {

        Intent in = getIntent();
        med_user_id_f_btm = in.getIntExtra(SEND_USER_ID_TO_ADD_MED, -1);
        med_user_id_f_dwr = in.getIntExtra("AddMedicines Id", -1);
        med_user_id_f_rem_list = in.getIntExtra("id user in reminder list to add med", -1);
        med_med_id_f_rem_list = in.getIntExtra("id med in reminder list to add med", -1);

        med_med_id_f_rem_list_modify = in.getIntExtra("id med in reminder list to modify med", -1);

        if (med_user_id_f_btm != -1) {
            id_user = med_user_id_f_btm;
        }
        if (med_user_id_f_dwr != -1) {
            id_user = med_user_id_f_dwr;
        }
        if (med_user_id_f_rem_list != -1) {
            id_user = med_user_id_f_rem_list;
        }

        if (med_med_id_f_rem_list_modify != -1) {
            getInfoForModify(med_med_id_f_rem_list_modify);
            btn_save.setText("Modify");
        }


    }


    //*//status bar
    private void StatusBar() {

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.colorAccentAddMed2));
        }
    }

    /**
     * toolbar
     **/
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

    boolean t = true;

    /***getting take medication*********/

    private void getMedTake() {
        charSp_med_take = ArrayAdapter.createFromResource(getBaseContext(), R.array.med_take, android.R.layout.simple_spinner_item);
        charSp_med_take.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_take.setAdapter(charSp_med_take);

        spinner_take.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                boolean t = false;
                if (med_med_id_f_rem_list_modify != -1) {
                    medDb.OpenDb();
                    MyMedicines myMedicines = medDb.getOneMedicine(med_med_id_f_rem_list_modify);
                    if (myMedicines.getMed_take().equals("Before the meal")) {
                        adapterView.setSelection(0);
                        med_take = charSp_med_take.getItem(0).toString();
                        t = true;
                    } else if (myMedicines.getMed_take().equals("With the meal")) {
                        adapterView.setSelection(1);
                        med_take = charSp_med_take.getItem(1).toString();
                        t = true;
                    } else if (myMedicines.getMed_take().equals("After the meal")) {
                        adapterView.setSelection(2);
                        med_take = charSp_med_take.getItem(2).toString();
                        t = true;
                    } else if (myMedicines.getMed_take().equals("Not matter")) {
                        adapterView.setSelection(3);
                        med_take = charSp_med_take.getItem(3).toString();
                        t = true;
                    }

                    if (t) {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                med_take = charSp_med_take.getItem(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                        //  Toast.makeText(AddMedicines.this, "" + med_take, Toast.LENGTH_SHORT).show();
                    }

                    //  Toast.makeText(AddMedicines.this, "" + med_take, Toast.LENGTH_SHORT).show();
                    medDb.CloseDb();
                } else {
                    med_take = charSp_med_take.getItem(i).toString();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    /**
     * getting medicines form by spinner
     **/
    private void getMedicineForm() {
        charSp_med_form = ArrayAdapter.createFromResource(getBaseContext(), R.array.med_form, android.R.layout.simple_spinner_item);
        charSp_med_form.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_mForm.setAdapter(charSp_med_form);
        spinner_mForm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                boolean t = false;
                if (med_med_id_f_rem_list_modify != -1) {
                    medDb.OpenDb();
                    MyMedicines myMedicines = medDb.getOneMedicine(med_med_id_f_rem_list_modify);
                    if (myMedicines.getMed_form().equals("Oral")) {
                        getSubFormOral();
                        cardView_subForm2.setVisibility(View.VISIBLE);
                        card_subForm2.setVisibility(View.VISIBLE);
                        tv_sub_form1.setText("" + charSp_med_form.getItem(0).toString() + " form");
                        med_form = charSp_med_form.getItem(0).toString();
                        adapterView.setSelection(0);
                        t = true;
                    } else if (myMedicines.getMed_form().equals("Dermal")) {
                        getSubFormDermal();
                        cardView_subForm2.setVisibility(View.GONE);
                        card_subForm2.setVisibility(View.GONE);
                        tv_sub_form1.setText("" + charSp_med_form.getItem(1).toString() + " form");
                        med_form = charSp_med_form.getItem(1).toString();

                        adapterView.setSelection(1);
                        t = true;

                    } else if (myMedicines.getMed_form().equals("Injectable")) {
                        getSubFormInjectable();
                        cardView_subForm2.setVisibility(View.GONE);
                        card_subForm2.setVisibility(View.GONE);
                        tv_sub_form1.setText("" + charSp_med_form.getItem(2).toString() + " form");
                        med_form = charSp_med_form.getItem(2).toString();
                        adapterView.setSelection(2);
                        t = true;
                    } else if (myMedicines.getMed_form().equals("Transmucosal")) {
                        getSubFormTransmucosal();
                        cardView_subForm2.setVisibility(View.GONE);
                        card_subForm2.setVisibility(View.GONE);

                        tv_sub_form1.setText("" + charSp_med_form.getItem(3).toString() + " form");
                        med_form = charSp_med_form.getItem(3).toString();

                        adapterView.setSelection(3);
                        t = true;
                    }

                    if (t) {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                if (i == 0) {
                                    getSubFormOral();
                                    cardView_subForm2.setVisibility(View.VISIBLE);
                                    card_subForm2.setVisibility(View.VISIBLE);

                                }
                                if (i == 1) {

                                    cardView_subForm2.setVisibility(View.GONE);
                                    card_subForm2.setVisibility(View.GONE);
                                    getSubFormDermal();
                                    med_sub_form2 = "";
                                    med_dose = "";


                                }
                                if (i == 2) {
                                    getSubFormInjectable();
                                    cardView_subForm2.setVisibility(View.GONE);
                                    card_subForm2.setVisibility(View.GONE);
                                }
                                if (i == 3) {
                                    getSubFormTransmucosal();
                                    cardView_subForm2.setVisibility(View.GONE);
                                    card_subForm2.setVisibility(View.GONE);
                                }

                                tv_sub_form1.setText("" + charSp_med_form.getItem(i).toString() + " form");
                                med_form = charSp_med_form.getItem(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }


                } else {

                    if (i == 0) {
                        getSubFormOral();
                        cardView_subForm2.setVisibility(View.VISIBLE);
                        card_subForm2.setVisibility(View.VISIBLE);

                    }
                    if (i == 1) {
                        getSubFormDermal();
                        cardView_subForm2.setVisibility(View.GONE);
                        card_subForm2.setVisibility(View.GONE);
                    }
                    if (i == 2) {
                        getSubFormInjectable();
                        cardView_subForm2.setVisibility(View.GONE);
                        card_subForm2.setVisibility(View.GONE);
                    }
                    if (i == 3) {
                        getSubFormTransmucosal();
                        cardView_subForm2.setVisibility(View.GONE);
                        card_subForm2.setVisibility(View.GONE);
                    }
                    tv_sub_form1.setText("" + charSp_med_form.getItem(i).toString() + " form");
                    med_form = charSp_med_form.getItem(i).toString();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * getting from of medicine form by spinner
     **/
    /**
     * get sub form of oral
     ***/
    private void getSubFormOral() {
        charSp_sub_form1 = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.med_sub_form1_oral, android.R.layout.simple_spinner_item);
        charSp_sub_form1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subForm1.setAdapter(charSp_sub_form1);

        spinner_subForm1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                boolean t = false;
                if (med_med_id_f_rem_list_modify != -1) {

                    if (i == 0) {
                        getDriedForm();
                        tv_dose.setText("mg");
                    }
                    if (i == 1) {
                        getLiquidForm();
                        tv_dose.setText("ml");
                    }
                    tv_sub_form2.setText("" + charSp_sub_form1.getItem(i).toString() + " form");
                    med_sub_form = charSp_sub_form1.getItem(i).toString();
                    medDb.OpenDb();
                    MyMedicines myMedicines = medDb.getOneMedicine(med_med_id_f_rem_list_modify);

                    if (myMedicines.getMed_sub_form().equals("Dried")) {
                        adapterView.setSelection(0);
                        getDriedForm();
                        tv_dose.setText("mg");
                        tv_sub_form2.setText("" + charSp_sub_form1.getItem(0).toString() + " form");
                        med_sub_form = charSp_sub_form1.getItem(0).toString();

                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Liquid")) {
                        adapterView.setSelection(1);
                        getLiquidForm();
                        tv_dose.setText("ml");
                        tv_sub_form2.setText("" + charSp_sub_form1.getItem(1).toString() + " form");
                        med_sub_form = charSp_sub_form1.getItem(1).toString();

                        t = true;
                    }

                    if (t) {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                if (i == 0) {
                                    getDriedForm();
                                    tv_dose.setText("mg");
                                }
                                if (i == 1) {
                                    getLiquidForm();
                                    tv_dose.setText("ml");
                                }
                                tv_sub_form2.setText("" + charSp_sub_form1.getItem(i).toString() + " form");
                                med_sub_form = charSp_sub_form1.getItem(i).toString();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                if (i == 0) {
                                    getDriedForm();
                                    tv_dose.setText("mg");
                                }
                                if (i == 1) {
                                    getLiquidForm();
                                    tv_dose.setText("ml");
                                }
                                tv_sub_form2.setText("" + charSp_sub_form1.getItem(i).toString() + " form");
                                med_sub_form = charSp_sub_form1.getItem(i).toString();
                                Toast.makeText(AddMedicines.this, "" + med_sub_form, Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }


                    medDb.CloseDb();
                } else {
                    if (i == 0) {
                        getDriedForm();
                        tv_dose.setText("mg");
                    }
                    if (i == 1) {
                        getLiquidForm();
                        tv_dose.setText("ml");
                    }
                    tv_sub_form2.setText("" + charSp_sub_form1.getItem(i).toString() + " form");
                    med_sub_form = charSp_sub_form1.getItem(i).toString();
                    Toast.makeText(AddMedicines.this, "" + med_sub_form, Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * get dried form
     ***/
    private void getDriedForm() {
        charSp_sub_form2 = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.med_sub_form2_dried, android.R.layout.simple_spinner_item);
        charSp_sub_form2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subForm2.setAdapter(charSp_sub_form2);
        spinner_subForm2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                boolean t = false;
                if (med_med_id_f_rem_list_modify != -1) {
                    med_sub_form2 = charSp_sub_form2.getItem(i).toString();
                    medDb.OpenDb();
                    MyMedicines myMedicines = medDb.getOneMedicine(med_med_id_f_rem_list_modify);
                    if (myMedicines.getMed_sub_form2().equals("Compressed")) {
                        adapterView.setSelection(0);
                        med_sub_form2 = "Compressed";
                        t = true;
                    } else if (myMedicines.getMed_sub_form2().equals("Capsule")) {
                        adapterView.setSelection(1);
                        med_sub_form2 = "Capsule";
                        t = true;
                    }

                    if (t) {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                med_sub_form2 = charSp_sub_form2.getItem(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    medDb.CloseDb();
                } else {

                    med_sub_form2 = charSp_sub_form2.getItem(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    /**
     * get Liquid form
     ***/
    private void getLiquidForm() {
        charSp_sub_form2 = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.med_sub_form2_liquid, android.R.layout.simple_spinner_item);
        charSp_sub_form2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subForm2.setAdapter(charSp_sub_form2);
        spinner_subForm2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                boolean t = false;
                if (med_med_id_f_rem_list_modify != -1) {
                    med_sub_form2 = charSp_sub_form2.getItem(i).toString();
                    medDb.OpenDb();
                    MyMedicines myMedicines = medDb.getOneMedicine(med_med_id_f_rem_list_modify);
                    if (myMedicines.getMed_sub_form2().equals("Syrup")) {
                        adapterView.setSelection(0);
                        //     med_sub_form2 = charSp_sub_form2.getItem(0).toString();
                        med_sub_form2 = "Syrup";
                        t = true;
                    } else if (myMedicines.getMed_sub_form2().equals("Drop")) {
                        adapterView.setSelection(1);
//                        med_sub_form2 = charSp_sub_form2.getItem(1).toString();
                        med_sub_form2 = "Drop";
                        t = true;
                    } else if (myMedicines.getMed_sub_form2().equals("Ampoule")) {
                        adapterView.setSelection(2);
                        med_sub_form2 = "Ampoule";
                        //med_sub_form2 = charSp_sub_form2.getItem(2).toString();
                        t = true;
                    }

                    if (t) {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                med_sub_form2 = charSp_sub_form2.getItem(i).toString();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    medDb.CloseDb();
                } else {

                    med_sub_form2 = charSp_sub_form2.getItem(i).toString();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * get sub form of dermal
     ***/
    private void getSubFormDermal() {
        charSp_sub_form1 = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.med_sub_form1_dermal, android.R.layout.simple_spinner_item);
        charSp_sub_form1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subForm1.setAdapter(charSp_sub_form1);

        spinner_subForm1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                boolean t = false;
                if (med_med_id_f_rem_list_modify != -1) {
                    med_sub_form = charSp_sub_form1.getItem(i).toString();
                    med_sub_form2 = "";
                    medDb.OpenDb();
                    MyMedicines myMedicines = medDb.getOneMedicine(med_med_id_f_rem_list_modify);
                    if (myMedicines.getMed_sub_form().equals("Ointment")) {
                        med_sub_form = charSp_sub_form1.getItem(0).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(0);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Cream")) {
                        med_sub_form = charSp_sub_form1.getItem(1).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(1);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Gel")) {
                        med_sub_form = charSp_sub_form1.getItem(2).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(2);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Sawon")) {
                        med_sub_form = charSp_sub_form1.getItem(3).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(3);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Patch")) {
                        med_sub_form = charSp_sub_form1.getItem(4).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(4);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Lotions")) {
                        med_sub_form = charSp_sub_form1.getItem(5).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(5);
                        t = true;
                    }

                    if (t) {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                med_sub_form = charSp_sub_form1.getItem(i).toString();
                                med_sub_form2 = "";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                med_sub_form = charSp_sub_form1.getItem(i).toString();
                                med_sub_form2 = "";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    medDb.CloseDb();
                } else {
                    med_sub_form = charSp_sub_form1.getItem(i).toString();
                    med_sub_form2 = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * get sub form of dermal
     ***/
    private void getSubFormInjectable() {
        charSp_sub_form1 = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.med_sub_form1_injectable, android.R.layout.simple_spinner_item);
        charSp_sub_form1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subForm1.setAdapter(charSp_sub_form1);

        spinner_subForm1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                boolean t = false;
                if (med_med_id_f_rem_list_modify != -1) {
                    med_sub_form = charSp_sub_form1.getItem(i).toString();
                    med_sub_form2 = "";
                    medDb.OpenDb();
                    MyMedicines myMedicines = medDb.getOneMedicine(med_med_id_f_rem_list_modify);
                    if (myMedicines.getMed_sub_form().equals("Intramuscular Route")) {
                        med_sub_form = charSp_sub_form1.getItem(0).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(0);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Intravenous Route")) {
                        med_sub_form = charSp_sub_form1.getItem(1).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(1);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Subcutaneous")) {
                        med_sub_form = charSp_sub_form1.getItem(2).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(2);
                        t = true;
                    }

                    if (t) {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                med_sub_form = charSp_sub_form1.getItem(i).toString();
                                med_sub_form2 = "";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    medDb.CloseDb();
                } else {
                    med_sub_form = charSp_sub_form1.getItem(i).toString();
                    med_sub_form2 = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    /**
     * get sub form of
     ***/
    private void getSubFormTransmucosal() {
        charSp_sub_form1 = ArrayAdapter.createFromResource(getBaseContext(),
                R.array.med_sub_form1_transmucosal, android.R.layout.simple_spinner_item);
        charSp_sub_form1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_subForm1.setAdapter(charSp_sub_form1);

        spinner_subForm1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                boolean t = false;
                if (med_med_id_f_rem_list_modify != -1) {
                    med_sub_form = charSp_sub_form1.getItem(i).toString();
                    med_sub_form2 = "";
                    medDb.OpenDb();
                    MyMedicines myMedicines = medDb.getOneMedicine(med_med_id_f_rem_list_modify);
                    if (myMedicines.getMed_sub_form().equals("Ocular")) {
                        med_sub_form = charSp_sub_form1.getItem(0).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(0);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Pulmonary")) {
                        med_sub_form = charSp_sub_form1.getItem(1).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(1);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Rectal")) {
                        med_sub_form = charSp_sub_form1.getItem(2).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(2);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("Vaginal")) {
                        med_sub_form = charSp_sub_form1.getItem(3).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(3);
                        t = true;
                    } else if (myMedicines.getMed_sub_form().equals("ORL")) {
                        med_sub_form = charSp_sub_form1.getItem(4).toString();
                        med_sub_form2 = "";
                        adapterView.setSelection(4);
                        t = true;
                    }

                    if (t) {
                        adapterView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                med_sub_form = charSp_sub_form1.getItem(i).toString();
                                med_sub_form2 = "";
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    medDb.CloseDb();
                } else {
                    med_sub_form = charSp_sub_form1.getItem(i).toString();
                    med_sub_form2 = "";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    /***
     * getting img med from database
     * ***/

    private void getImgMed() {
        ArrayList<ImageMed> imageMeds = new ArrayList<>();
        imageMedDb.OpenDb();


        ImageMed imageMed = imageMedDb.getImage(1);
        if (imageMed.getImg_med() != null && med_med_id_f_rem_list_modify == -1) {
            img_med.setImageBitmap(BitmapFactory.decodeByteArray(imageMed.getImg_med(), 0, imageMed.getImg_med().length));
        }
        imageMeds = imageMedDb.getImagesMed();
        imageMedDb.CloseDb();
        adtImg = new RecAdtImg(imageMeds, new OnClick_imgMed() {
            @Override
            public void onClickImgMed(int id_img_med) {
                getImg(id_img_med);
            }
        });

        layoutImg = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        rec_img_med.setAdapter(adtImg);
        rec_img_med.setLayoutManager(layoutImg);
    }

    /**
     * Method for getting pictures for user
     **/

    private void getImg(int id_img) {

        imageMedDb.OpenDb();
        ImageMed imageMed = imageMedDb.getImage(id_img);
        img_med.setImageBitmap(BitmapFactory.decodeByteArray(imageMed.getImg_med(),
                0, imageMed.getImg_med().length));
        sImg = "imgDb says yes";
        imageMedDb.CloseDb();

    }

    /****
     * This Method helps you to save image into database
     *
     * */
    private byte[] imgMed(ImageView img) {
        if (sImg.equals("imgDb says yes")) {
            Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] bytes = outputStream.toByteArray();
            return bytes;
        } else {
            return null;
        }
    }

    /**
     * button save medicine
     ***/
    private void btnSaveMedicine() {
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (med_med_id_f_rem_list_modify != -1) {

                    modifyMedicine(med_med_id_f_rem_list_modify);
                } else {
                    saveMedicine();
                }
            }
        });
    }


    /***save medicines***/
    private void saveMedicine() {

        boolean resS;
        med_name = et_med_name.getText().toString();
        med_dose = et_med_dose.getText().toString();
        med_img = imgMed(img_med);
        MyMedicines myMedicines = new MyMedicines(med_name, med_take, med_form, med_sub_form, med_sub_form2, med_dose, med_img, id_user);
        medDb.OpenDb();
        if (et_med_name.length() == 0) {
            et_med_name.setError("");
        } else if (et_med_dose.length() == 0 && med_form.equals("Oral")) {
            et_med_dose.setError("");
        } else if (medDb.medExist(myMedicines)) {
            Toast.makeText(this, "This medicine exist!", Toast.LENGTH_SHORT).show();
        } else {
            resS = medDb.saveMedicine(myMedicines);
            if (resS) {
                MyMedicines myMedicines1 = medDb.getMedicineId(myMedicines);
                Intent iReminder = new Intent(getBaseContext(), AlarmReminder.class);
                iReminder.putExtra(ID_MED_TO_REMINDER, myMedicines1.getId_med());
                iReminder.putExtra(ID_USER_TO_REMINDER, id_user);
                startActivity(iReminder);
                Toast.makeText(this, " Saved ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Not saved", Toast.LENGTH_SHORT).show();
            }

        }

        medDb.CloseDb();
    }


    /****put info for modify medicine  ******/
    private void getInfoForModify(int med_id) {

        medDb.OpenDb();
        MyMedicines myMedicines = medDb.getOneMedicine(med_id);
        et_med_name.setText(myMedicines.getMed_name());
        et_med_dose.setText(myMedicines.getMed_dose());
        med_dose = myMedicines.getMed_dose();
        med_name = myMedicines.getMed_name();
        med_form = myMedicines.getMed_form();
        med_sub_form = myMedicines.getMed_sub_form();
        med_sub_form2 = myMedicines.getMed_sub_form2();
        if (myMedicines.getMed_image() != null) {

            img_med.setImageBitmap(BitmapFactory.decodeByteArray(myMedicines.getMed_image(), 0, myMedicines.getMed_image().length));

        }

        medDb.CloseDb();

    }

    private void modifyMedicine(int med_id) {

        boolean resM;
        med_name = et_med_name.getText().toString();
        med_dose = et_med_dose.getText().toString();
        med_img = imgMed(img_med);
        MyMedicines myMedicines = new MyMedicines(med_id, med_name, med_take, med_form, med_sub_form, med_sub_form2, med_dose, med_img, id_user);
        medDb.OpenDb();
        if (et_med_name.length() == 0) {
            et_med_name.setError("");
        } else if (et_med_dose.length() == 0 && med_form.equals("Oral")) {
            et_med_dose.setError("");
        } else if (medDb.medExist(myMedicines)) {

            MyMedicines myMedicine = new MyMedicines(med_id, med_take, med_form, med_sub_form, med_sub_form2, med_dose, med_img, id_user);

            if (medDb.modifyMedWithout(myMedicine)) {
                MyMedicines myMedicines1 = medDb.getMedicineId(myMedicines);
                Intent iReminder = new Intent(getBaseContext(), ReminderList.class);
                iReminder.putExtra(ID_MED_TO_REMINDER, myMedicines1.getId_med());
                iReminder.putExtra(ID_USER_TO_REMINDER, id_user);
                startActivity(iReminder);
                Toast.makeText(this, " Modify ", Toast.LENGTH_SHORT).show();
            }

        } else {
            resM = medDb.modifyMed(myMedicines);
            if (resM) {
                MyMedicines myMedicines1 = medDb.getMedicineId(myMedicines);
                Intent iReminder = new Intent(getBaseContext(), ReminderList.class);
                iReminder.putExtra(ID_MED_TO_REMINDER, myMedicines1.getId_med());
                iReminder.putExtra(ID_USER_TO_REMINDER, id_user);
                startActivity(iReminder);
                Toast.makeText(this, " Modify ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Not modify", Toast.LENGTH_SHORT).show();
            }

        }

        medDb.CloseDb();


    }


}