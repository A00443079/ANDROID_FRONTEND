package com.example.staybuddy.ui.home;

public class Hotel {
    private String hotelName;
    private int price;
    private boolean availability;
    private String image;

    public Hotel(String hotelName, int price, boolean availability) {
        this.hotelName = hotelName;
        this.price = price;
        this.availability = availability;
    }

    public Hotel(String hotelName, int price, boolean availability, String image) {
        this.hotelName = hotelName;
        this.price = price;
        this.availability = availability;
        this.image = image;
    }

    public boolean isAvailable() {
        return availability;
    }

    public int getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getHotelName() {
        return hotelName;
    }
}
