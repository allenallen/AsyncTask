package com.hanselandpetal.catalog;

public class StockModel {

    private String name;
    private String close;
    private double price;
    private double pctChange;


    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPctChange() {
        return pctChange;
    }

    public void setPctChange(double pctChange) {
        this.pctChange = pctChange;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
