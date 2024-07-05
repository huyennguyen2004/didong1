package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> categories;

    public CategoryAdapter(Context context, List<String> categories) {
        super(context, 0, categories);
        this.context = context;
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String category = categories.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        }

        TextView categoryTextView = convertView.findViewById(R.id.category_name);
        categoryTextView.setText(category);

        return convertView;
    }
}
