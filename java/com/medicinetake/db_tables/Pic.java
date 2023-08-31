package com.medicinetake.db_tables;

public class Pic {

    private int id_pic_user;
    private byte[] pic;

    public Pic(int id_pic_user, byte[] pic) {
        this.id_pic_user = id_pic_user;
        this.pic = pic;
    }

    public int getId_pic_user() {
        return id_pic_user;
    }

    public void setId_pic_user(int id_pic_user) {
        this.id_pic_user = id_pic_user;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }
}
