package com.example.staybuddy.ui.login;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.staybuddy.MainActivity;
import com.example.staybuddy.R;
import com.example.staybuddy.data.LoginDataSource;
import com.example.staybuddy.data.LoginRepository;
import com.example.staybuddy.data.model.LoggedInUser;
import com.example.staybuddy.helpers.HttpUtils;
import com.example.staybuddy.ui.login.LoginViewModel;
import com.example.staybuddy.ui.login.LoginViewModelFactory;
import com.example.staybuddy.databinding.ActivityLoginBinding;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;
        final TextView registerText = binding.register;
        final TextView errorView = binding.loginError;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Created Request body
                    JSONObject body = new JSONObject();
                    body.put("email", usernameEditText.getText().toString());
                    body.put("password", passwordEditText.getText().toString());

                    //Requested login by accessing endpoint
                    HttpUtils.post("login", new StringEntity(body.toString()), new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                Boolean success = response.getBoolean("success");
                                //If success, show "login successful" toast and starting the main activity, and storing logged in user in login repository(singleton)
                                if (success) {
                                    LoginRepository repo = LoginRepository.getInstance(null);
                                    repo.setLoggedInUser(new LoggedInUser(usernameEditText.getText().toString(), usernameEditText.getText().toString()));
                                    Toast t = new Toast(getApplicationContext());
                                    t.setText(response.getString("message"));
                                    t.setDuration(Toast.LENGTH_LONG);
                                    t.show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    LoginActivity.this.startActivity(intent);
                                } else {
                                    // else show error in the UI
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

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //When register is clicked, opening signup page
                Intent intentSignup = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(intentSignup);
            }
        });
    }
}