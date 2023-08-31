package com.medicinetake.db_tables;

public class ReminderMed {

    private int id_reminder;
    private String time;
    private String date;
    private String repeat_min;
    private String repeat_day;
    private String active;
    private String active_r;
    private int id_med_rem;

    public ReminderMed(int id_reminder) {
        this.id_reminder = id_reminder;
    }

    public ReminderMed(int id_reminder, String time, String date, String repeat_min, String repeat_day, String active, String active_r, int id_med_rem) {
        this.id_reminder = id_reminder;
        this.time = time;
        this.date = date;
        this.repeat_min = repeat_min;
        this.repeat_day = repeat_day;
        this.active = active;
        this.active_r = active_r;
        this.id_med_rem = id_med_rem;
    }

    public ReminderMed(String time, String date, String repeat_min, String repeat_day, String active, String active_r, int id_med_rem) {
        this.time = time;
        this.date = date;
        this.repeat_min = repeat_min;
        this.repeat_day = repeat_day;
        this.active = active;
        this.active_r = active_r;
        this.id_med_rem = id_med_rem;
    }

    public ReminderMed(String time, String date, int id_med_rem) {
        this.time = time;
        this.date = date;
        this.id_med_rem = id_med_rem;
    }

    public int getId_reminder() {
        return id_reminder;
    }

    public void setId_reminder(int id_reminder) {
        this.id_reminder = id_reminder;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRepeat_min() {
        return repeat_min;
    }

    public void setRepeat_min(String repeat_min) {
        this.repeat_min = repeat_min;
    }

    public String getRepeat_day() {
        return repeat_day;
    }

    public void setRepeat_day(String repeat_day) {
        this.repeat_day = repeat_day;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getActive_r() {
        return active_r;
    }

    public void setActive_r(String active_r) {
        this.active_r = active_r;
    }

    public int getId_med_rem() {
        return id_med_rem;
    }

    public void setId_med_rem(int id_med_rem) {
        this.id_med_rem = id_med_rem;
    }
}
