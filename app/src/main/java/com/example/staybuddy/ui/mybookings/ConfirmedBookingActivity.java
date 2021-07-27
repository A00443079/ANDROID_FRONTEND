package com.example.staybuddy.ui.mybookings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staybuddy.MainActivity;
import com.example.staybuddy.R;
import com.example.staybuddy.databinding.ActivityBookingBinding;
import com.example.staybuddy.databinding.ActivityConfirmedBookingBinding;
import com.example.staybuddy.helpers.HttpUtils;
import com.example.staybuddy.ui.booking.Guest;
import com.example.staybuddy.ui.login.LoginActivity;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ConfirmedBookingActivity extends AppCompatActivity {

    private ActivityConfirmedBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmedBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final TextView confirmationNumberTextView = binding.confirmationNumber;
        final TextView hotelName = binding.bookedHotelName;
        final TextView checkInDate = binding.bookedCheckIn;
        final TextView checkOutDate = binding.bookedCheckOut;
        final Button cancelButton = binding.cancelBookingButton;
        String confirmationNumber = getIntent().getStringExtra("confirmation_number");
        confirmationNumberTextView.setText(confirmationNumber);

        RequestParams rp = new RequestParams();
        rp.add("id", confirmationNumber);
        //Getting the reservation details using the confirmation numbers
        HttpUtils.get("getReservationDetails", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                RecyclerView bookedGuestsView = binding.bookedGuestsView;
                try {
                    hotelName.setText(response.getString("hotel_name"));
                    checkInDate.setText("Check-In: "+response.getString("checkin"));
                    checkOutDate.setText("Check-Out: "+response.getString("checkout"));
                    ArrayList<Guest> guestList = new ArrayList<>();
                    JSONArray arr = response.getJSONArray("guests_list");
                    int l = arr.length();
                    for (int i=0; i<l; i++) {
                        JSONObject jsonObject = (JSONObject) arr.get(i);
                        Guest g = new Guest(jsonObject.getString("guest_name"), jsonObject.getString("gender"));
                        guestList.add(g);
                    }
                    BookedGuestsAdapter adapter = new BookedGuestsAdapter(guestList);
                    bookedGuestsView.setAdapter(adapter);
                    bookedGuestsView.setLayoutManager(new LinearLayoutManager(ConfirmedBookingActivity.this));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams rp = new RequestParams();
                rp.add("id", confirmationNumber);
                //Cancelling the booking provided the confirmation_number
                HttpUtils.get("cancel", rp, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            //Upon successful cancellation, showing a toast to user on the screen
                            Toast t = new Toast(getApplicationContext());
                            t.setText(response.getString("message"));
                            t.setDuration(Toast.LENGTH_LONG);
                            t.show();
                            Intent intent = new Intent(ConfirmedBookingActivity.this, MainActivity.class);
                            ConfirmedBookingActivity.this.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}