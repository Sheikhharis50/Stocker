package com.example.sheik.stocker;

import android.provider.ContactsContract;

public class product {

    private int pCode;
    private String title;
    private String company;
    private int Stock;
    private String price;
    private String description;
    private int Image;

    public product(int pCode, String title, String company, int stock, String price, String description, int Image) {
        this.pCode = pCode;
        this.title = title;
        this.company = company;
        Stock = stock;
        this.price = price;
        this.description = description;
        this.Image = Image;
    }

    public int getpCode() {
        return pCode;
    }

    public int getImage() {
        return Image;
    }

    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public int getStock() {
        return Stock;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
