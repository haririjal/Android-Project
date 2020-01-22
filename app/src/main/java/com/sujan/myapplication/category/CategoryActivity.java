package com.sujan.myapplication.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.sujan.myapplication.MainActivity;
import com.sujan.myapplication.R;
import com.sujan.myapplication.listener.OnClickListener;

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<Category> categoryList = new ArrayList<>();
    private CategoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initToolbar();
        getData();
        findViews();
        initRecyclerView();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Category Activity");
    }

    private void findViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryAdapter(categoryList, new OnClickListener() {
            @Override
            public void onClick(int position) {
                Category obj= categoryList.get(position);
                Intent intent= new Intent(CategoryActivity.this, MainActivity.class);
                intent.putExtra("id", obj.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void getData() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        RealmResults<Category> results = realm.where(Category.class).findAll();
        realm.commitTransaction();
        if (results.size() > 0)
            categoryList.addAll(results);

    }


}
