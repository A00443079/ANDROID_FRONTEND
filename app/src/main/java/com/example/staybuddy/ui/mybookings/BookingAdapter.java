package com.example.staybuddy.ui.mybookings;


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

public class BookingAdapter extends
        RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<Booking> mBookings;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView booking;

        public ViewHolder(View itemView) {
            super(itemView);

            booking = (TextView) itemView.findViewById(R.id.bookingIdClickable);
        }
    }

    public BookingAdapter(List<Booking> bookings) {
        mBookings = bookings;
    }

    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookingsView = inflater.inflate(R.layout.item_booking_id, parent, false);

        // Return a new holder instance
        BookingAdapter.ViewHolder viewHolder = new BookingAdapter.ViewHolder(bookingsView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(BookingAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Booking booking = mBookings.get(position);

        // Set item views based on your views and data model
        TextView bookingId = holder.booking;

        bookingId.setText(booking.getBookingId());

        bookingId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConfirmedBookingActivity.class);
                //sending confirmation_number to athe ConfirmedBookingActivity
                intent.putExtra("confirmation_number", booking.getBookingId());
                v.getContext().startActivity(intent);
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mBookings.size();
    }
}