package com.example.staybuddy.ui.mybookings;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class MyBookingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyBookingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}