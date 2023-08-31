package com.medicinetake.db_tables;

public class User {

    private int id_user;
    private String username;
    private String user_age;
    private String user_gender;
    private byte[] user_img;

    public User(String user_age, String user_gender, byte[] user_img) {
        this.user_age = user_age;
        this.user_gender = user_gender;
        this.user_img = user_img;
    }

    public User(int id_user, String username, String user_age, String user_gender, byte[] user_img) {
        this.id_user = id_user;
        this.username = username;
        this.user_age = user_age;
        this.user_gender = user_gender;
        this.user_img = user_img;
    }

    public User(String username, String user_age, String user_gender, byte[] user_img) {
        this.username = username;
        this.user_age = user_age;
        this.user_gender = user_gender;
        this.user_img = user_img;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public byte[] getUser_img() {
        return user_img;
    }

    public void setUser_img(byte[] user_img) {
        this.user_img = user_img;
    }
}
