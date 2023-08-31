package com.medicinetake.db_tables;

public class MyMedicines {


    private int id_med;
    private String med_name;
    private String med_take;
    private String med_form;
    private String med_sub_form;
    private String med_sub_form2;
    private String med_dose;
    private byte[] med_image;
    private int med_user_id;


    public MyMedicines(String med_name, String med_take, String med_form,
                       String med_sub_form, String med_sub_form2, String med_dose, byte[] med_image, int med_user_id) {
        this.med_name = med_name;
        this.med_take = med_take;
        this.med_form = med_form;
        this.med_sub_form = med_sub_form;
        this.med_sub_form2 = med_sub_form2;
        this.med_dose = med_dose;
        this.med_image = med_image;
        this.med_user_id = med_user_id;
    }

    public MyMedicines(int id_med, String med_name, String med_take, String med_form,
                       String med_sub_form, String med_sub_form2, String med_dose, byte[] med_image, int med_user_id) {
        this.id_med = id_med;
        this.med_name = med_name;
        this.med_take = med_take;
        this.med_form = med_form;
        this.med_sub_form = med_sub_form;
        this.med_sub_form2 = med_sub_form2;
        this.med_dose = med_dose;
        this.med_image = med_image;
        this.med_user_id = med_user_id;
    }

    public String getMed_take() {
        return med_take;
    }

    public void setMed_take(String med_take) {
        this.med_take = med_take;
    }

    public MyMedicines(int id_med, String med_name, String med_form, String med_sub_form, String med_sub_form2, int med_user_id) {
        this.id_med = id_med;
        this.med_name = med_name;
        this.med_form = med_form;
        this.med_sub_form = med_sub_form;
        this.med_sub_form2 = med_sub_form2;
        this.med_user_id = med_user_id;
    }


    public MyMedicines(int id_med, String med_take, String med_form, String med_sub_form, String med_sub_form2, String med_dose, byte[] med_image, int med_user_id) {
        this.id_med = id_med;
        this.med_take = med_take;
        this.med_form = med_form;
        this.med_sub_form = med_sub_form;
        this.med_sub_form2 = med_sub_form2;
        this.med_dose = med_dose;
        this.med_image = med_image;
        this.med_user_id = med_user_id;
    }

    public MyMedicines(int id_med, String med_name) {
        this.id_med = id_med;
        this.med_name = med_name;
    }


    public int getId_med() {
        return id_med;
    }

    public void setId_med(int id_med) {
        this.id_med = id_med;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getMed_form() {
        return med_form;
    }

    public void setMed_form(String med_form) {
        this.med_form = med_form;
    }

    public String getMed_sub_form() {
        return med_sub_form;
    }

    public void setMed_sub_form(String med_sub_form) {
        this.med_sub_form = med_sub_form;
    }

    public String getMed_sub_form2() {
        return med_sub_form2;
    }

    public void setMed_sub_form2(String med_sub_form2) {
        this.med_sub_form2 = med_sub_form2;
    }

    public String getMed_dose() {
        return med_dose;
    }

    public void setMed_dose(String med_dose) {
        this.med_dose = med_dose;
    }

    public byte[] getMed_image() {
        return med_image;
    }

    public void setMed_image(byte[] med_image) {
        this.med_image = med_image;
    }

    public int getMed_user_id() {
        return med_user_id;
    }

    public void setMed_user_id(int med_user_id) {
        this.med_user_id = med_user_id;
    }
}
