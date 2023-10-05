package com.ttdat.application.model;

public class OrderDetail {
    int orderID;
    int medicineID;
    int quantity;

    public OrderDetail(int orderID, int medicineID, int quantity) {
        this.orderID = orderID;
        this.medicineID = medicineID;
        this.quantity = quantity;
    }

    public OrderDetail() {

    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(int medicineID) {
        this.medicineID = medicineID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
