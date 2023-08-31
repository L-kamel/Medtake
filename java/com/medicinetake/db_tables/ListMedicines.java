package com.medicinetake.db_tables;

public class ListMedicines {

    private int id_list_med;
    private String list_med_name;
    private String list_reference;
    private String list_form;
    private String list_dose;
    private String list_indication;
    private String list_dosage;
    private byte[] list_img_med;
    private int list_id_category;


    public ListMedicines(int id_list_med, String list_med_name,
                         String list_reference, String list_form, String list_dose,
                         String list_indication, String list_dosage, byte[] list_img_med, int list_id_category) {
        this.id_list_med = id_list_med;
        this.list_med_name = list_med_name;
        this.list_reference = list_reference;
        this.list_form = list_form;
        this.list_dose = list_dose;
        this.list_indication = list_indication;
        this.list_dosage = list_dosage;
        this.list_img_med = list_img_med;
        this.list_id_category = list_id_category;
    }

    public int getId_list_med() {
        return id_list_med;
    }

    public void setId_list_med(int id_list_med) {
        this.id_list_med = id_list_med;
    }

    public String getList_med_name() {
        return list_med_name;
    }

    public void setList_med_name(String list_med_name) {
        this.list_med_name = list_med_name;
    }

    public String getList_reference() {
        return list_reference;
    }

    public void setList_reference(String list_reference) {
        this.list_reference = list_reference;
    }

    public String getList_form() {
        return list_form;
    }

    public void setList_form(String list_form) {
        this.list_form = list_form;
    }

    public String getList_dose() {
        return list_dose;
    }

    public void setList_dose(String list_dose) {
        this.list_dose = list_dose;
    }

    public String getList_indication() {
        return list_indication;
    }

    public void setList_indication(String list_indication) {
        this.list_indication = list_indication;
    }

    public String getList_dosage() {
        return list_dosage;
    }

    public void setList_dosage(String list_dosage) {
        this.list_dosage = list_dosage;
    }

    public byte[] getList_img_med() {
        return list_img_med;
    }

    public void setList_img_med(byte[] list_img_med) {
        this.list_img_med = list_img_med;
    }

    public int getList_id_category() {
        return list_id_category;
    }

    public void setList_id_category(int list_id_category) {
        this.list_id_category = list_id_category;
    }
}
