package com.example.banhangonline.Model;

import com.google.gson.annotations.SerializedName;

public class Customer {
    @SerializedName("maKhachHang")
    public int customerCode;
    @SerializedName("tenKhachHang")
    private String customerName;
    @SerializedName("soDienThoai")
    private String customerPhoneNumber;
    @SerializedName("email")
    private String customerEmail;
    @SerializedName("diaChi")
    private String customerAdress;
    @SerializedName("tenDangNhap")
    private String cutomerUserName;
    @SerializedName("matKhau")
    private String customerPassword;

    public int getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(int customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerAdress() {
        return customerAdress;
    }

    public void setCustomerAdress(String customerAdress) {
        this.customerAdress = customerAdress;
    }

    public String getCutomerUserName() {
        return cutomerUserName;
    }

    public void setCutomerUserName(String cutomerUserName) {
        this.cutomerUserName = cutomerUserName;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public Customer(){

    }

    public Customer(int customerCode, String customerName, String customerPhoneNumber, String customerEmail, String customerAdress, String cutomerUserName, String customerPassword) {
        this.customerCode = customerCode;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerEmail = customerEmail;
        this.customerAdress = customerAdress;
        this.cutomerUserName = cutomerUserName;
        this.customerPassword = customerPassword;
    }
}
