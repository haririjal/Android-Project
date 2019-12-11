package com.sujan.myapplication.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.sujan.myapplication.R;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<Category> categoryList= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initToolbar();
        findViews();
        setData();
        initRecyclerView();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Category Activity");
    }

    private void findViews() {
        recyclerView= findViewById(R.id.recyclerView);
    }

    private void setData(){
        Category cate1= new Category();
        Category cate2= new Category();
        Category cate3= new Category();
        cate1.setTitle("Non-Veg Restaurant");
        cate2.setTitle("Veg Restaurant");
        cate3.setTitle("Drinks");
        cate1.setDescription("Order your favorite food from Restaurant.");
        cate2.setDescription("Pure vegetarian Restaurant");
        cate3.setDescription("Drinks home delivery.");
        categoryList.add(cate1);
        categoryList.add(cate2);
        categoryList.add(cate3);

    }

    private void initRecyclerView(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
    }
}
