package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.models.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private ListView cartListView;
    private CartAdapter cartAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartListView = findViewById(R.id.cart_list_view);
        fetchCartItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchCartItems();
    }

    private void fetchCartItems() {
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<List<Cart>> call = methods.getCarts();
        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Cart> carts = response.body();
                    cartAdapter = new CartAdapter(CartActivity.this, carts);
                    cartListView.setAdapter(cartAdapter);
                } else {
                    Toast.makeText(CartActivity.this, "Failed to retrieve carts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Log.e("API Error", "Error fetching carts", t);
                Toast.makeText(CartActivity.this, "Error fetching carts: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateCartListView(List<Cart> carts) {
        if (cartAdapter == null) {
            cartAdapter = new CartAdapter(CartActivity.this, carts);
            cartListView.setAdapter(cartAdapter);
        } else {
            cartAdapter.setCartItems(carts); // Update adapter's data
            cartAdapter.notifyDataSetChanged(); // Notify adapter of data change
        }
    }

}
