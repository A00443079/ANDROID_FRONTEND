package com.example.staybuddy.ui.booking;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staybuddy.MainActivity;
import com.example.staybuddy.R;
import com.example.staybuddy.data.LoginRepository;
import com.example.staybuddy.data.model.LoggedInUser;
import com.example.staybuddy.databinding.ActivityBookingBinding;
import com.example.staybuddy.databinding.ActivitySignupBinding;
import com.example.staybuddy.helpers.HttpUtils;
import com.example.staybuddy.ui.home.Hotel;
import com.example.staybuddy.ui.home.HotelsAdapter;
import com.example.staybuddy.ui.home.SelectedHotel;
import com.example.staybuddy.ui.login.LoginActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class BookingActivity extends AppCompatActivity {

    private ActivityBookingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final EditText checkInDate = binding.checkInDate;
        final EditText checkOutDate = binding.checkOutDate;
        final TextView hotelName = binding.hotelName;
        final TextView price = binding.hotelBookingPrice;
        final EditText numberOfGuests = binding.guests;
        final Button confirmBooking = binding.hotelBooking;

        Hotel hotel = SelectedHotel.getHotel();

        hotelName.setText(hotel.getHotelName());
        price.setText("Price: $0");

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener checkInDatePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                checkInDate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
            }

        };

        DatePickerDialog.OnDateSetListener checkOutDatePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                checkOutDate.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                try {
                    Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse(checkInDate.getText().toString());
                    Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse(checkOutDate.getText().toString());
                    long diff = d2.getTime() - d1.getTime();
                    long days = diff / (1000 * 3600 * 24);
                    price.setText("Price: $"+(days * hotel.getPrice()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        };

        checkInDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(BookingActivity.this, checkInDatePicker, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        checkOutDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(BookingActivity.this, checkOutDatePicker, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        numberOfGuests.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int guests = Integer.parseInt(numberOfGuests.getText().toString());
                    if (guests > 0) {
                        RecyclerView guestsView = binding.guestsView;
                        ArrayList<Guest> guestArrayList = Guest.createGuests(guests);
                        GuestList guestList = GuestList.getInstance(guestArrayList);
                        guestList.setGuests(guestArrayList);
                        GuestAdapter adapter = new GuestAdapter(guestList.getGuests());
                        guestsView.setAdapter(adapter);
                        guestsView.setLayoutManager(new LinearLayoutManager(BookingActivity.this));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        confirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LoginRepository repo = LoginRepository.getInstance(null);
                    //Creating request body
                    JSONObject body = new JSONObject();

                    body.put("email", repo.getUser().getUserId());
                    body.put("hotel_name", hotelName.getText().toString());
                    body.put("checkin", checkInDate.getText().toString());
                    body.put("checkout", checkOutDate.getText().toString());
                    JSONArray guests = new JSONArray();
                    GuestList guestList = GuestList.getInstance(null);
                    for (int i=0; i<guestList.getGuests().size();i++) {
                        JSONObject guest = new JSONObject();
                        guest.put("guest_name", guestList.getGuests().get(i).getGuestName());
                        guest.put("gender", guestList.getGuests().get(i).getGender());
                        guests.put(guest);
                    }
                    body.put("guests_list", guests);

                    //Calling bookingConfirmation endpoint
                    HttpUtils.post("bookingConfirmation", new StringEntity(body.toString()), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                //upon successful booking, showing a toast to user
                                String confirmation = response.getString("confirmation_number");
                                Toast t = new Toast(getApplicationContext());
                                t.setText("Booking successful");
                                t.setDuration(Toast.LENGTH_LONG);
                                t.show();
                                Intent intent = new Intent(BookingActivity.this, BookingConfirmationActivity.class);
                                intent.putExtra("booking_id", confirmation);
                                BookingActivity.this.startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}