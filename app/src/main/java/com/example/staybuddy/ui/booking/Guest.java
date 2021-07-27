package com.example.staybuddy.ui.booking;


import java.util.ArrayList;

public class Guest {
    private String guestName;
    private String gender;

    public Guest(String guestName, String gender) {
        this.guestName = guestName;
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public static ArrayList<Guest> createGuests(int numGuests) {
        ArrayList<Guest> guests = new ArrayList<Guest>();

        for (int i = 1; i <= numGuests; i++) {
            guests.add(new Guest("", ""));
        }

        return guests;
    }
}
