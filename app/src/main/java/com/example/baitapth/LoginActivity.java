package com.example.baitapth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private TextView tvErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);

        findViewById(R.id.btnLogin).setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        // Sample account check: admin / 123456
        if ("admin".equalsIgnoreCase(username) && "123456".equals(password)) {
            tvErrorMessage.setVisibility(View.GONE);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            tvErrorMessage.setVisibility(View.VISIBLE);
        }
    }
}
