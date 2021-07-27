package com.example.staybuddy.ui.mybookings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.staybuddy.R;
import com.example.staybuddy.ui.booking.Guest;

import java.util.List;

public class BookedGuestsAdapter extends
        RecyclerView.Adapter<BookedGuestsAdapter.ViewHolder> {

    private List<Guest> mBookedGuests;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView guestName, guestGender;

        public ViewHolder(View itemView) {
            super(itemView);

            guestName = (TextView) itemView.findViewById(R.id.bookedGuestName);
            guestGender = (TextView) itemView.findViewById(R.id.bookedGuestGender);
        }
    }

    public BookedGuestsAdapter(List<Guest> guests) {
        mBookedGuests = guests;
    }

    @Override
    public BookedGuestsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View bookedGuestsView = inflater.inflate(R.layout.item_booked_guest, parent, false);

        // Return a new holder instance
        BookedGuestsAdapter.ViewHolder viewHolder = new BookedGuestsAdapter.ViewHolder(bookedGuestsView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(BookedGuestsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Guest guest = mBookedGuests.get(position);

        // Set item views based on your views and data model
        TextView guestName = holder.guestName;
        TextView guestGender = holder.guestGender;

        guestName.setText("Guest Name: "+guest.getGuestName());
        guestGender.setText("Gender: "+guest.getGender());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mBookedGuests.size();
    }
}
