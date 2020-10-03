package com.example.fancyfood;

import android.net.Uri;

import java.util.ArrayList;

public class CreateUser {
    String name,address,year,phoneno,area,cuisines;
    public CreateUser() {
    }

    public CreateUser(String name, String address, String year, String phoneno, String area, String cuisines) {
        this.name = name;
        this.address = address;
        this.year = year;
        this.phoneno = phoneno;
        this.area = area;
        this.cuisines = cuisines;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }
}
