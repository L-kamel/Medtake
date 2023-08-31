package com.medicinetake.db_tables;

public class Categories {

    private int id_categ;
    private String categ_name;

    public Categories(int id_categ, String categ_name) {
        this.id_categ = id_categ;
        this.categ_name = categ_name;
    }


    public int getId_categ() {
        return id_categ;
    }

    public void setId_categ(int id_categ) {
        this.id_categ = id_categ;
    }

    public String getCateg_name() {
        return categ_name;
    }

    public void setCateg_name(String categ_name) {
        this.categ_name = categ_name;
    }
}
