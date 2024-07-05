package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.Cart;
import com.example.myapplication.models.CartProduct;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private List<Cart> cartItems;
    private LayoutInflater inflater;

    public CartAdapter(Context context, List<Cart> cartItems) {
        this.cartItems = cartItems;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public Object getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cart, parent, false);
        }

        CheckBox cartCheckbox = convertView.findViewById(R.id.cart_checkbox);
        ImageView productImage = convertView.findViewById(R.id.cart_product_image);
        TextView productName = convertView.findViewById(R.id.product_name);
        ImageView buttonIncrease = convertView.findViewById(R.id.button_increase);
        TextView productQuantity = convertView.findViewById(R.id.product_quantity);
        ImageView buttonDecrease = convertView.findViewById(R.id.button_decrease);
        ImageView buttonDelete = convertView.findViewById(R.id.button_delete);

        Cart cart = cartItems.get(position);
        CartProduct cartProduct = cart.getProducts().get(0); // Assuming you display the first product for simplicity


        Glide.with(convertView.getContext())
                .load("https://fakestoreapi.com/img/" + cartProduct.getProductId() + ".jpg") // Example URL
                .into(productImage);
        productName.setText("Product " + cartProduct.getProductId()); // Example name
        productQuantity.setText(String.valueOf(cartProduct.getQuantity()));

        cartCheckbox.setChecked(cart.isChecked());
        cartCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            cart.setChecked(isChecked);
        });

        buttonIncrease.setOnClickListener(v -> {
            int quantity = cartProduct.getQuantity();
            cartProduct.setQuantity(quantity + 1);
            productQuantity.setText(String.valueOf(cartProduct.getQuantity()));
        });

        buttonDecrease.setOnClickListener(v -> {
            int quantity = cartProduct.getQuantity();
            if (quantity > 1) {
                cartProduct.setQuantity(quantity - 1);
                productQuantity.setText(String.valueOf(cartProduct.getQuantity()));
            }
        });

        buttonDelete.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyDataSetChanged();
        });
        return convertView;}
        public void setCartItems(List<Cart> cartItems) {
            this.cartItems = cartItems;
        }
    }

