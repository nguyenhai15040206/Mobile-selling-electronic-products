package com.example.banhangonline.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Invoice {
    @SerializedName("maHoaDon")
    private int maHoaDon;
    @SerializedName("ngayBan")
    private Date ngayBan;
    @SerializedName("ngayGiao")
    private Date ngayGiao;
    @SerializedName("maKhachHang")
    private int maKhachHang;
    @SerializedName("tienBan")
    private double tienBan;
    @SerializedName("giamGia")
    private double giamGia;
    @SerializedName("tongTien")
    private double tongTien;
    @SerializedName("ghiChu")
    private String ghiChu;
    @SerializedName("maNguoiDung")
    private Integer maNguoiDung;
    @SerializedName("tinhTrang")
    private boolean tinhTrang;

    public Invoice() {
    }

    public Invoice(int maHoaDon, Date ngayBan, Date ngayGiao, int maKhachHang, double tienBan, double giamGia, double tongTien, String ghiChu, Integer maNguoiDung, boolean tinhTrang) {
        this.maHoaDon = maHoaDon;
        this.ngayBan = ngayBan;
        this.ngayGiao = ngayGiao;
        this.maKhachHang = maKhachHang;
        this.tienBan = tienBan;
        this.giamGia = giamGia;
        this.tongTien = tongTien;
        this.ghiChu = ghiChu;
        this.maNguoiDung = maNguoiDung;
        this.tinhTrang = tinhTrang;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Date getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(Date ngayBan) {
        this.ngayBan = ngayBan;
    }

    public Date getNgayGiao() {
        return ngayGiao;
    }

    public void setNgayGiao(Date ngayGiao) {
        this.ngayGiao = ngayGiao;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public double getTienBan() {
        return tienBan;
    }

    public void setTienBan(double tienBan) {
        this.tienBan = tienBan;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Integer getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(int maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
