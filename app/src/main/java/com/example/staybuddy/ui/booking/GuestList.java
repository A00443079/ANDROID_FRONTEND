package com.example.staybuddy.ui.booking;

import com.example.staybuddy.ui.home.Hotel;
import com.example.staybuddy.ui.home.SelectedHotel;

import java.util.ArrayList;

public class GuestList {
    private static volatile GuestList instance;
    private ArrayList<Guest> guests;

    public GuestList(ArrayList<Guest> guests) {
        this.guests = guests;
    }

    public static GuestList getInstance(ArrayList<Guest> guests) {
        if (instance == null) {
            instance = new GuestList(guests);
        }
        return instance;
    }

    public void setGuests(ArrayList<Guest> guests) {
        this.guests = guests;
    }

    public ArrayList<Guest> getGuests() {
        return guests;
    }
}
