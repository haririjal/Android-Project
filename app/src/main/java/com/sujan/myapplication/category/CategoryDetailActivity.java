package com.sujan.myapplication.category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sujan.myapplication.R;

import io.realm.Realm;

public class CategoryDetailActivity extends AppCompatActivity {
    private int id;
    private Category category;
    private TextView txtTitle;
    private TextView txtDescription;
    private EditText edtDescription;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        id = getIntent().getIntExtra("id", 0);
        if (id != 0) {
            Log.d("IntentValue", String.valueOf(id));
        }
        getCategory();
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);
        edtDescription = findViewById(R.id.edtDescription);
        btnUpdate = findViewById(R.id.btnUpdate);
        txtTitle.setText(category.getTitle());
        txtDescription.setText(category.getDescription());
        edtDescription.setText(category.getDescription());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCategory();
            }
        });
    }

    private void getCategory() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        category = realm.where(Category.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
    }

    private void updateCategory() {
        Category category=new Category();
        category.setId(this.category.getId());
        category.setTitle(this.category.getTitle());
        category.setDescription(edtDescription.getText().toString().trim());
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        category.deleteFromRealm();
        realm.deleteAll();
        realm.copyToRealmOrUpdate(category);
        realm.commitTransaction();
        Toast.makeText(this, "Category data updated successfully.", Toast.LENGTH_SHORT).show();
    }
}
