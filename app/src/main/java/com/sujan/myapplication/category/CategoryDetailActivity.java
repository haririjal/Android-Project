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
import com.sujan.myapplication.api.ApiService;
import com.sujan.myapplication.api.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryDetailActivity extends AppCompatActivity {
    private int id;
    private Category category;
    private TextView txtTitle;
    private TextView txtDescription;
    private EditText edtDescription;
    private Button btnUpdate;
    private List<Result> movieResult = new ArrayList<>();
    ApiService service = RetrofitHelper.getClient().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        callMovieApi();
        id = getIntent().getIntExtra("id", 0);
        if (id != 0) {
            Log.d("IntentValue", String.valueOf(id));
        }
        showMovieData();
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

    private void showMovieData() {
        for (int i = 0; i < movieResult.size(); i++) {
            Log.d("Title", movieResult.get(i).getTitle());
        }
    }

    private void getCategory() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        category = realm.where(Category.class).equalTo("id", id).findFirst();
        realm.commitTransaction();
    }

    private void updateCategory() {
        Category category = new Category();
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

    private void callMovieApi() {
        Call<Movie> call = service.getMovieData("ef8f48b43832a9e95b87408bf739ed51");
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movie = response.body();
                    movieResult.addAll(movie.getResults());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("Error", t.getLocalizedMessage());
            }
        });
    }
}
