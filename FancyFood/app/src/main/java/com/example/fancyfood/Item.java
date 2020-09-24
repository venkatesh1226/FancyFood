package com.example.fancyfood;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
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


    protected Item(Parcel in) {
        id = in.readString();
        name = in.readString();
        price = in.readInt();
        image = in.readString();
        hotelId = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeInt(price);
        parcel.writeString(image);
        parcel.writeString(hotelId);
    }
}
