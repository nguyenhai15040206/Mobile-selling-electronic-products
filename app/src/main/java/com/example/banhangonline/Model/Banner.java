package com.example.banhangonline.Model;

import com.google.gson.annotations.SerializedName;

public class Banner {

    @SerializedName("maBanner")
    public  int maBanner;
    @SerializedName("fileBanner")
    public  String fileBanner;
    @SerializedName("kichHoat")
    public  boolean kichHoat;
    @SerializedName("ghiChu")
    public  String ghiChu;

    public int getMaBanner() {
        return maBanner;
    }

    public void setMaBanner(int maBanner) {
        this.maBanner = maBanner;
    }

    public String getFileBanner() {
        return fileBanner;
    }

    public void setFileBanner(String fileBanner) {
        this.fileBanner = fileBanner;
    }
    public boolean isKichHoat() {
        return kichHoat;
    }

    public void setKichHoat(boolean kichHoat) {
        this.kichHoat = kichHoat;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Banner(){}

    public Banner(int maBanner, String fileBanner, boolean kichHoat, String ghiChu) {
        this.maBanner = maBanner;
        this.fileBanner = fileBanner;
        this.kichHoat = kichHoat;
        this.ghiChu = ghiChu;
    }
}
