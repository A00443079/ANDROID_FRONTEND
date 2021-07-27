package com.example.staybuddy.ui.booking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.staybuddy.MainActivity;
import com.example.staybuddy.R;
import com.example.staybuddy.databinding.ActivityBookingBinding;
import com.example.staybuddy.databinding.ActivityBookingConfirmationBinding;

public class BookingConfirmationActivity extends AppCompatActivity {

    private ActivityBookingConfirmationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingConfirmationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final TextView bookingId = binding.bookingConfirmationId;
        final Button okButton = binding.ok;

        String bookingIdFromIntent = getIntent().getStringExtra("booking_id");

        bookingId.setText("Confirmation Id: "+bookingIdFromIntent);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingConfirmationActivity.this, MainActivity.class);
                BookingConfirmationActivity.this.startActivity(intent);
            }
        });
    }
}