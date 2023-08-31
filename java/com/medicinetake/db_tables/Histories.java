package com.medicinetake.db_tables;

public class Histories {

    private int id_history;
    private String med_name_h;
    private String time_h;
    private String date_h;
    private String take_history;
    private int id_med_history;
    private int id_user_history;

    public Histories(int id_history, String med_name_h, String time_h, String date_h, String take_history, int id_med_history, int id_user_history) {
        this.id_history = id_history;
        this.med_name_h = med_name_h;
        this.time_h = time_h;
        this.date_h = date_h;
        this.take_history = take_history;
        this.id_med_history = id_med_history;
        this.id_user_history = id_user_history;
    }


    public Histories(String med_name_h, String time_h, String date_h, String take_history, int id_med_history, int id_user_history) {
        this.med_name_h = med_name_h;
        this.time_h = time_h;
        this.date_h = date_h;
        this.take_history = take_history;
        this.id_med_history = id_med_history;
        this.id_user_history = id_user_history;
    }

    public int getId_history() {
        return id_history;
    }

    public void setId_history(int id_history) {
        this.id_history = id_history;
    }

    public String getTake_history() {
        return take_history;
    }

    public void setTake_history(String take_history) {
        this.take_history = take_history;
    }

    public String getMed_name_h() {
        return med_name_h;
    }

    public void setMed_name_h(String med_name_h) {
        this.med_name_h = med_name_h;
    }

    public String getTime_h() {
        return time_h;
    }

    public void setTime_h(String time_h) {
        this.time_h = time_h;
    }

    public String getDate_h() {
        return date_h;
    }

    public void setDate_h(String date_h) {
        this.date_h = date_h;
    }

    public int getId_med_history() {
        return id_med_history;
    }

    public void setId_med_history(int id_med_history) {
        this.id_med_history = id_med_history;
    }

    public int getId_user_history() {
        return id_user_history;
    }

    public void setId_user_history(int id_user_history) {
        this.id_user_history = id_user_history;
    }
}
