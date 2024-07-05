package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.models.User;
import com.example.myapplication.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword, editTextPhone;
    private Button buttonSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Ánh xạ các thành phần giao diện
        editTextFirstName = findViewById(R.id.etSignupFirstName);
        editTextLastName = findViewById(R.id.etSignupLastName);
        editTextUsername = findViewById(R.id.etSignupUserName);
        editTextEmail = findViewById(R.id.etSignupEmail);
        editTextPassword = findViewById(R.id.etSignupPassword);
        editTextConfirmPassword = findViewById(R.id.etSignupConfirmPassword);
        editTextPhone = findViewById(R.id.etSignupPhone);
        buttonSignup = findViewById(R.id.btnSignup);

        buttonSignup.setOnClickListener(v -> {
            String firstName = editTextFirstName.getText().toString();
            String lastName = editTextLastName.getText().toString();
            String username = editTextUsername.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            String confirmPassword = editTextConfirmPassword.getText().toString();
            String phone = editTextPhone.getText().toString();

            if (!password.equals(confirmPassword)) {
                Toast.makeText(SignupActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            User.Name name = new User.Name(firstName, lastName);
            User user = new User(username, password, email, name, phone);

            Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
            Call<UserResponse> call = methods.signupUser(user);
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(SignupActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        // Chuyển đến LoginActivity sau khi đăng ký thành công
                        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Đóng SignupActivity để ngăn người dùng quay lại
                    } else {
                        Toast.makeText(SignupActivity.this, "Đăng ký thất bại.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(SignupActivity.this, "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
