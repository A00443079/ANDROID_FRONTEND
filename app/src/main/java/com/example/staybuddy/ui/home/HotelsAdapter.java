package com.example.staybuddy.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.staybuddy.ui.booking.BookingActivity;
import com.example.staybuddy.R;

import java.util.List;

public class HotelsAdapter extends
        RecyclerView.Adapter<HotelsAdapter.ViewHolder> {

    private List<Hotel> mHotels;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView hotelName, hotelPrice, hotelAvailability;
        Button hotelBook;

        public ViewHolder(View itemView) {
            super(itemView);

            hotelName = (TextView) itemView.findViewById(R.id.hotelName);
            hotelAvailability = (TextView) itemView.findViewById(R.id.hotelAvailability);
            hotelPrice = (TextView) itemView.findViewById(R.id.hotelPrice);
            hotelBook = (Button) itemView.findViewById(R.id.hotelBook);
        }
    }

    public HotelsAdapter(List<Hotel> hotels) {
        mHotels = hotels;
    }

    @Override
    public HotelsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View hotelView = inflater.inflate(R.layout.item_hotel, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(hotelView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(HotelsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Hotel hotel = mHotels.get(position);

        // Set item views based on your views and data model
        TextView hotelNameView = holder.hotelName;
        TextView hotelPriceView = holder.hotelPrice;
        TextView hotelAvailabilityView = holder.hotelAvailability;
        Button hotelBook = holder.hotelBook;
        hotelNameView.setText(hotel.getHotelName());
        hotelPriceView.setText("Price: $" + hotel.getPrice());
        hotelAvailabilityView.setText(hotel.isAvailable() ? "Available" : "Not Available");
        if (!hotel.isAvailable()) {
            hotelBook.setEnabled(false);
            hotelBook.setBackgroundColor(Color.RED);
        }
        hotelBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setting the selected hotel when Book button is clicked
                SelectedHotel.setHotel(hotel);
                Intent intent = new Intent(v.getContext(), BookingActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mHotels.size();
    }
}