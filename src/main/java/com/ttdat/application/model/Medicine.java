package com.ttdat.application.model;


public class Medicine {
    private int medicineID;
    private String brand;
    private String productName;
    private String type;
    private float price;
    private String status;

    public Medicine(int medicineID, String brand, String productName, String type, float price, String status) {
        this.medicineID = medicineID;
        this.brand = brand;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public Medicine(String brand, String productName, String type, float price, String status) {
        this.brand = brand;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.status = status;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
