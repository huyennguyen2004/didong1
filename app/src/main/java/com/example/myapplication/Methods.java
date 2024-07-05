package com.example.myapplication;

import com.example.myapplication.models.Cart;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.ProductDetail;
//
import com.example.myapplication.models.LoginRequest;
import com.example.myapplication.models.LoginResponse;
//
import com.example.myapplication.models.User;
import com.example.myapplication.models.UserResponse;
//
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
public interface Methods {
    @GET("products")
    Call<List<Product>> getProducts();
    @GET("products/{id}")
    Call<ProductDetail> getProductDetail(@Path("id") int productId);
    @GET("products/categories")
    Call<List<String>> getCategories();
    @GET("carts")
    Call<List<Cart>> getCarts();
    @GET("carts/{cartId}")
    Call<Cart> getCart(@Path("cartId") int cartId);
    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
    @POST("users")
    Call<UserResponse> signupUser(@Body User user);
}
