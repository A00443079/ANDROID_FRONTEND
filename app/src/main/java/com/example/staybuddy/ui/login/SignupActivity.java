package com.example.staybuddy.ui.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staybuddy.R;
import com.example.staybuddy.databinding.ActivityLoginBinding;
import com.example.staybuddy.databinding.ActivitySignupBinding;
import com.example.staybuddy.helpers.HttpUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class SignupActivity extends AppCompatActivity {

    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Button signupBtn = binding.signup;
        final TextView errorView = binding.signupError;
        final TextView email = binding.emailSignup;
        final TextView password = binding.signupPassword;
        final TextView cPassword = binding.signupConfirmPassword;

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject body = new JSONObject();
                try {
                    body.put("email", email.getText());
                    body.put("password", password.getText());
                    body.put("confirm_password", cPassword.getText());

                    HttpUtils.post("signup", new StringEntity(body.toString()), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                Boolean success = response.getBoolean("success");
                                if (success) {
                                    Toast t = new Toast(getApplicationContext());
                                    t.setText(response.getString("message"));
                                    t.setDuration(Toast.LENGTH_LONG);
                                    t.show();
                                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                    SignupActivity.this.startActivity(intent);
                                } else {
                                    errorView.setText(response.getString("error"));
                                }
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