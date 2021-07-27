package com.example.staybuddy.ui.booking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.staybuddy.R;

import java.util.List;

public class GuestAdapter extends
        RecyclerView.Adapter<GuestAdapter.ViewHolder> {

    private List<Guest> mGuests;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public EditText guestName;
        public RadioGroup gender;

        public ViewHolder(View itemView) {
            super(itemView);

            guestName = (EditText) itemView.findViewById(R.id.guestName);
            gender = (RadioGroup) itemView.findViewById(R.id.guestGender);
        }
    }

    public GuestAdapter(List<Guest> guests) {
        mGuests = guests;
    }

    @Override
    public GuestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View guestsView = inflater.inflate(R.layout.item_guest, parent, false);

        // Return a new holder instance
        GuestAdapter.ViewHolder viewHolder = new GuestAdapter.ViewHolder(guestsView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GuestAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Guest guest = mGuests.get(position);

        // Set item views based on your views and data model
        EditText guestName = holder.guestName;
        RadioGroup gender = holder.gender;

        guestName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                guest.setGuestName(guestName.getText().toString());
            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                guest.setGender(checkedRadioButton.getText().toString());
            }
        });
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mGuests.size();
    }
}