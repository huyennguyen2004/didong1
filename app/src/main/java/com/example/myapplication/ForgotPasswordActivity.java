package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        sendButton = findViewById(R.id.button);

        sendButton.setOnClickListener(v -> sendPasswordResetEmail());
    }

    private void sendPasswordResetEmail() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }

        // Logic to send a password reset email and OTP would go here
        Toast.makeText(this, "OTP has been sent to " + email, Toast.LENGTH_SHORT).show();

        // Redirect to OtpActivity
        Intent intent = new Intent(ForgotPasswordActivity.this, OtpActivity.class);
        startActivity(intent);
        finish(); // Optional: Close ForgotPasswordActivity so user can't go back to it
    }
}
