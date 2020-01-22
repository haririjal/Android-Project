package com.sujan.myapplication.category;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sujan.myapplication.R;
import com.sujan.myapplication.listener.OnClickListener;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryVH> {
    private ArrayList<Category> categoryList = new ArrayList<>();
    private OnClickListener listener;

    public CategoryAdapter(ArrayList<Category> categoryList, final OnClickListener listener) {
        this.categoryList.addAll(categoryList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_category_item, parent, false);
        return new CategoryVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVH holder, final int position) {
        Category obj = categoryList.get(position);
        holder.txtTitle.setText(obj.getTitle());
        holder.txtDescription.setText(obj.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
