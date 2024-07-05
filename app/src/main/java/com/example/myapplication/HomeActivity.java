package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.models.Product;
import com.google.android.material.navigation.NavigationView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private Toolbar toolbar;
    private Banner banner;
    private GridView productGridView;
    private GridView gridViewCategories;
    private SearchView searchView;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private PostAdapter postAdapter;
    private ListView postListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("");
        drawerLayout = findViewById(R.id.drawer_layout);
        navView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        banner = findViewById(R.id.banner);
        productGridView = findViewById(R.id.grid_view);
        gridViewCategories = findViewById(R.id.grid_view_categories);
        postListView = findViewById(R.id.post_list_view);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_login) {
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_signup) {
                    Intent intent = new Intent(HomeActivity.this, SignupActivity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        List<Integer> bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.banner1);
        bannerImages.add(R.drawable.banner1);
        bannerImages.add(R.drawable.banner1);

        banner.setAdapter(new BannerImageAdapter<Integer>(bannerImages) {
                    @Override
                    public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                        holder.imageView.setImageResource(data);
                    }
                })
                .addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this));

        ImageView cartIcon = findViewById(R.id.cart_icon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (productAdapter != null) {
                    productAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        fetchProductsFromApi();
        fetchCategoriesFromApi();
        fetchPosts();
    }

    private void fetchProductsFromApi() {
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<List<Product>> call = methods.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();
                    productAdapter = new ProductAdapter(HomeActivity.this, products);
                    productGridView.setAdapter(productAdapter);

                    productGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Product selectedProduct = (Product) productAdapter.getItem(position);
                            Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                            intent.putExtra("product_id", selectedProduct.getId());
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(HomeActivity.this, "Failed to retrieve products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("API Error", "Error fetching products", t);
                Toast.makeText(HomeActivity.this, "Error fetching products: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCategoriesFromApi() {
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<List<String>> call = methods.getCategories();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> categories = response.body();
                    categoryAdapter = new CategoryAdapter(HomeActivity.this, categories);
                    gridViewCategories.setAdapter(categoryAdapter);
                } else {
                    Toast.makeText(HomeActivity.this, "Không thể lấy danh mục sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("Lỗi API", "Lỗi khi lấy danh mục sản phẩm", t);
                Toast.makeText(HomeActivity.this, "Lỗi khi lấy danh mục sản phẩm: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(R.drawable.post1, "Post Title 1", "Post Content 1"));
        posts.add(new Post(R.drawable.post2, "Post Title 2", "Post Content 2"));
        posts.add(new Post(R.drawable.post3, "Post Title 3", "Post Content 3"));

        postAdapter = new PostAdapter(this, posts);
        postListView.setAdapter(postAdapter);
    }
}
