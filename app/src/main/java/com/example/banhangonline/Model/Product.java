package com.example.banhangonline.Model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Product {

    @SerializedName("maSanPham")
    public  int maSanPham;
    @SerializedName("tenSanPham")
    public  String tenSanPham;
    @SerializedName("soLuong")
    public  int soLuong;
    @SerializedName("donGia")
    public  double donGia;
    @SerializedName("donGiaNhap")
    public  double donGiaNhap;
    @SerializedName("moTa")
    public  String moTa;
    @SerializedName("moTaChiTiet")
    public  String moTaChiTiet;
    @SerializedName("khuyenMai")
    public  String khuyenMai;
    @SerializedName("giamGia")
    public  double giamGia;
    @SerializedName("ngayCapNhat")
    public Date ngayCapNhat;
    @SerializedName("xuatXu")
    public  String xuatXu;
    @SerializedName("hinhMinhHoa")
    public  String hinhMinhHoa;
    @SerializedName("dsHinh")
    public  String dsHinh;
    @SerializedName("tinhTrang")
    public  boolean tinhTrang;
    @SerializedName("ghiChu")
    public String ghiChu;



    @SerializedName("tenDanhMuc")
    public String tenDanhMuc;

    public Product() {
    }

    public Product(int maSanPham, String tenSanPham, int soLuong, double donGia, double donGiaNhap,
                   String moTa, String moTaChiTiet, String khuyenMai, double giamGia, Date ngayCapNhat,
                   String xuatXu, String hinhMinhHoa, String dsHinh, boolean tinhTrang, String ghiChu, String tenDanhMuc) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.donGiaNhap = donGiaNhap;
        this.moTa = moTa;
        this.moTaChiTiet = moTaChiTiet;
        this.khuyenMai = khuyenMai;
        this.giamGia = giamGia;
        this.ngayCapNhat = ngayCapNhat;
        this.xuatXu = xuatXu;
        this.hinhMinhHoa = hinhMinhHoa;
        this.dsHinh = dsHinh;
        this.tinhTrang = tinhTrang;
        this.ghiChu = ghiChu;
        this.tenDanhMuc = tenDanhMuc;
    }


    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMoTaChiTiet() {
        return moTaChiTiet;
    }

    public void setMoTaChiTiet(String moTaChiTiet) {
        this.moTaChiTiet = moTaChiTiet;
    }

    public String getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(String khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public double getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(double giamGia) {
        this.giamGia = giamGia;
    }

    public Date getNgayCapNhat() {
        return ngayCapNhat;
    }

    public void setNgayCapNhat(Date ngayCapNhat) {
        this.ngayCapNhat = ngayCapNhat;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getHinhMinhHoa() {
        return hinhMinhHoa;
    }

    public void setHinhMinhHoa(String hinhMinhHoa) {
        this.hinhMinhHoa = hinhMinhHoa;
    }

    public String getDsHinh() {
        return dsHinh;
    }

    public void setDsHinh(String dsHinh) {
        this.dsHinh = dsHinh;
    }

    public boolean isTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(boolean tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
