package com.example.fancyfood;

import android.net.Uri;

public class Item {
    private String id,name;
    private int price;
    private String image;
    private String hotelId;

    public Item(String id, String name, int price, String image, String hotelId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.hotelId = hotelId;
    }
    public Item(){

    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
