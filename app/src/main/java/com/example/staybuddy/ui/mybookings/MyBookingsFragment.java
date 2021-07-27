package com.example.staybuddy.ui.mybookings;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;

import com.example.staybuddy.data.LoginRepository;
import com.example.staybuddy.databinding.FragmentMybookingsBinding;
import com.example.staybuddy.helpers.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MyBookingsFragment extends Fragment {

    private MyBookingsViewModel myBookingsViewModel;
    private FragmentMybookingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myBookingsViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(MyBookingsViewModel.class);

        binding = FragmentMybookingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myBookingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                LoginRepository repo = LoginRepository.getInstance(null);
                RecyclerView myBookingsView = binding.myBookingsView;
                RequestParams rp = new RequestParams();
                rp.add("email", repo.getUser().getUserId());
                //Getting List of confirmation numbers for the user identified by email
                HttpUtils.get("getListOfConfirmationNumbers", rp, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                        try {
                            int l = response.length();
                            ArrayList<Booking> bookingList = new ArrayList<>();
                            for (int i=0;i<l;i++) {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                Booking b = new Booking(jsonObject.getString("confirmation_number"));
                                bookingList.add(b);
                            }
                            BookingAdapter adapter = new BookingAdapter(bookingList);
                            myBookingsView.setAdapter(adapter);
                            myBookingsView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}