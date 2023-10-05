package com.ttdat.application.model;

import java.time.LocalDateTime;


public class Order {
    int orderID;
    String orderNumber;
    LocalDateTime createdDate;
    float totalValue;

    public Order(int orderID, String orderNumber, LocalDateTime createdDate, float totalValue) {
        this.orderID = orderID;
        this.orderNumber = orderNumber;
        this.createdDate = createdDate;
        this.totalValue = totalValue;
    }

    public Order(String orderNumber, LocalDateTime createdDate, float totalValue) {
        this.orderNumber = orderNumber;
        this.createdDate = createdDate;
        this.totalValue = totalValue;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
