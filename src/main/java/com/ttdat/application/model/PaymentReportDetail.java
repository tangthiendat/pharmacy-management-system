package com.ttdat.application.model;

public class PaymentReportDetail {
    String product;
    String price;
    String qty;
    String total;

    public PaymentReportDetail(String product, String price, String qty, String total) {
        this.product = product;
        this.price = price;
        this.qty = qty;
        this.total = total;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PaymentReportDetail{" +
                "product='" + product + '\'' +
                ", price='" + price + '\'' +
                ", qty='" + qty + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
