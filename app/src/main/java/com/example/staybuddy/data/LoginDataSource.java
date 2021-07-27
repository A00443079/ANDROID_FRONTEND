package com.example.staybuddy.data;

import android.content.Intent;
import android.widget.Toast;

import com.example.staybuddy.data.model.LoggedInUser;
import com.example.staybuddy.helpers.HttpUtils;
import com.example.staybuddy.ui.login.LoginActivity;
import com.example.staybuddy.ui.login.SignupActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        return new Result.Error(new IOException("Error logging in", new Exception("not supported")));
    }

    public void logout() {
        // TODO: revoke authentication
    }
}