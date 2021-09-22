package com.example.banhangonline.Model;

public class CartModel {
    public int productCode;
    public String productName;
    public String productImg;
    public  int productAmount;
    public double productUnitPrice;
    public double productSale;
    public String note;
    public String categoryName;

    public CartModel(int productCode, String productName, String productImg, int productAmount, double productUnitPrice, double productSale, String note,String categoryName) {
        this.productCode = productCode;
        this.productName = productName;
        this.productImg = productImg;
        this.productAmount = productAmount;
        this.productUnitPrice = productUnitPrice;
        this.productSale = productSale;
        this.note = note;
        this.categoryName = categoryName;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public double getProductUnitPrice() {
        return productUnitPrice;
    }

    public void setProductUnitPrice(double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    public double getProductSale() {
        return productSale;
    }

    public void setProductSale(double productSale) {
        this.productSale = productSale;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
