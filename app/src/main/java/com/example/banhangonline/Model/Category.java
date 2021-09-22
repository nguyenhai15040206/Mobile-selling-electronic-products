package com.example.banhangonline.Model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("maDanhMuc")
    public  int maDanhMuc;
    @SerializedName("tenDanhMuc")
    public  String tenDanhMuc;
    @SerializedName("ghiChu")
    public  String ghiChu;
    @SerializedName("logoTungDanhMucSp")
    public  String logoTungSanPham;

    public Category() {
    }

    public int getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(int maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getLogoTungSanPham() {
        return logoTungSanPham;
    }

    public void setLogoTungSanPham(String logoTungSanPham) {
        this.logoTungSanPham = logoTungSanPham;
    }

    public Category(int maDanhMuc, String tenDanhMuc, String ghiChu, String logoTungSanPham) {

        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
        this.ghiChu = ghiChu;
        this.logoTungSanPham = logoTungSanPham;
    }
}
