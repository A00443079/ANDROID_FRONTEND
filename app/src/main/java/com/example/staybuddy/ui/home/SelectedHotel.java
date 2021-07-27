package com.example.staybuddy.ui.home;

import com.example.staybuddy.data.LoginDataSource;
import com.example.staybuddy.data.LoginRepository;

public class SelectedHotel {
    private static volatile SelectedHotel instance;

    private Hotel hotel;

    public static SelectedHotel getInstance(Hotel hotel) {
        if (instance == null) {
            instance = new SelectedHotel(hotel);
        }
        return instance;
    }

    public SelectedHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public static void setHotel(Hotel hotel) {
        SelectedHotel selectedHotel = SelectedHotel.getInstance(hotel);
        selectedHotel.hotel = hotel;
    }

    public static Hotel getHotel() {
        SelectedHotel selectedHotel = SelectedHotel.getInstance(null);
        return selectedHotel.hotel;
    }
}
