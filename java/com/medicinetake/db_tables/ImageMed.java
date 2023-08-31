package com.medicinetake.db_tables;

public class ImageMed {

    private int id_img_med;
    private byte[] img_med;

    public ImageMed(int id_img_med, byte[] img_med) {
        this.id_img_med = id_img_med;
        this.img_med = img_med;
    }

    public int getId_img_med() {
        return id_img_med;
    }

    public void setId_img_med(int id_img_med) {
        this.id_img_med = id_img_med;
    }

    public byte[] getImg_med() {
        return img_med;
    }

    public void setImg_med(byte[] img_med) {
        this.img_med = img_med;
    }
}
