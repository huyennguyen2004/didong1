package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.ProductDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productDescription, productCategory;
    private Button addToCartButton;
    private ProductDetail productDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Khởi tạo các view
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        productCategory = findViewById(R.id.product_category);
        addToCartButton = findViewById(R.id.add_to_cart_button);

        // Lấy productId từ Intent
        int productId = getIntent().getIntExtra("product_id", -1);

        // Kiểm tra xem productId có hợp lệ không
        if (productId != -1) {
            // Gọi phương thức fetchProductDetail() để lấy chi tiết sản phẩm từ API
            fetchProductDetail(productId);
        } else {
            Toast.makeText(this, "Không có thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            finish(); // Đóng Activity nếu không có productId hợp lệ
        }

        // Thiết lập sự kiện khi nhấn vào nút "Thêm vào giỏ hàng"
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productDetail != null) {
                    addToCart(productDetail);
                }
            }
        });
    }

    // Phương thức để lấy chi tiết sản phẩm từ API
    private void fetchProductDetail(int productId) {
        // Tạo instance của Interface Methods từ RetrofitClient
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        // Gọi API để lấy chi tiết sản phẩm
        Call<ProductDetail> call = methods.getProductDetail(productId);

        call.enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(Call<ProductDetail> call, Response<ProductDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lưu thông tin sản phẩm vào biến productDetail và cập nhật giao diện
                    productDetail = response.body();
                    updateUI();
                } else {
                    // Hiển thị thông báo khi không thành công
                    Toast.makeText(ProductDetailActivity.this, "Không thể lấy thông tin sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductDetail> call, Throwable t) {
                // Hiển thị thông báo khi có lỗi trong quá trình gọi API
                Toast.makeText(ProductDetailActivity.this, "Lỗi khi lấy thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Phương thức cập nhật giao diện với thông tin sản phẩm đã lấy được
    private void updateUI() {
        if (productDetail != null) {
            // Hiển thị các thông tin của sản phẩm lên giao diện
            productName.setText(productDetail.getTitle());
            productPrice.setText(String.format("%,.2f đ", productDetail.getPrice())); // Định dạng giá tiền
            productDescription.setText(productDetail.getDescription());
            productCategory.setText(productDetail.getCategory());

            Glide.with(this)
                    .load(productDetail.getImage())
                    .into(productImage);
        }
    }

    // Phương thức thêm sản phẩm vào giỏ hàng
    private void addToCart(ProductDetail product) {
        // Gọi phương thức để thêm sản phẩm vào giỏ hàng\
        CartManager.getInstance().addToCart(product);
        Toast.makeText(ProductDetailActivity.this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
    }
}
