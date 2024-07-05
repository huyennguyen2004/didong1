package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OtpActivity extends AppCompatActivity {

    private EditText otpEditText;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEditText = findViewById(R.id.editTextOtp);
        verifyButton = findViewById(R.id.buttonVerifyOtp);

        verifyButton.setOnClickListener(v -> verifyOtp());
    }

    private void verifyOtp() {
        String otp = otpEditText.getText().toString().trim();

        if (otp.isEmpty()) {
            Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implement your logic to verify OTP
        boolean isOtpValid = true; // Replace with actual OTP verification logic

        if (isOtpValid) {
            // Redirect to ResetPasswordActivity
            Intent intent = new Intent(OtpActivity.this, ResetpasswordActivity.class);
            startActivity(intent);
            finish(); // Optional: Close OtpActivity so user can't go back to it
        } else {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
    }
}
