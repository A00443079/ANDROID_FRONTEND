package com.example.staybuddy.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;

import com.example.staybuddy.R;
import com.example.staybuddy.databinding.FragmentHomeBinding;
import com.example.staybuddy.helpers.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //creating new hotelsView recycler view
                RecyclerView hotelsView = binding.hotelsView;
                RequestParams rp = new RequestParams();

                //Getting hotels from the endpoints
                HttpUtils.get("hotels", rp, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.i("development", "success");
                        try {
                            //Creating hotelList to use in HotelsAdapter
                            ArrayList<Hotel> hotelList = new ArrayList<>();

                            JSONArray hotelsJson = (JSONArray) response.get("hotels_list");
                            int length = hotelsJson.length();
                            for (int i = 0; i < length; i++) {
                                //Creating Hotel object from the response
                                JSONObject hotelObject = (JSONObject) hotelsJson.get(i);
                                Hotel hotel = new Hotel(
                                        hotelObject.getString("hotel_name"),
                                        hotelObject.getInt("price"),
                                        hotelObject.getBoolean("availability")
                                );
                                //Adding Hotel object to hotelList
                                hotelList.add(hotel);
                            }
                            //Creating HotelsAdapter with the hotelList
                            HotelsAdapter adapter = new HotelsAdapter(hotelList);
                            //Setting the adapter to the recycler view
                            hotelsView.setAdapter(adapter);
                            //setting new layout
                            hotelsView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.i("development", "falied");
                    }

                    @Override
                    public void onFinish() {
                        Log.i("development", "finished");
                    }
                });
//                // Initialize contacts
//                contacts = Contact.createContactsList(20);
//                // Create adapter passing in the sample user data
//                ContactsAdapter adapter = new ContactsAdapter(contacts);
//                // Attach the adapter to the recyclerview to populate items
//                rvContacts.setAdapter(adapter);
//                // Set layout manager to position the items
//                rvContacts.setLayoutManager(new LinearLayoutManager(this));
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