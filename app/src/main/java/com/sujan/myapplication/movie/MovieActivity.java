package com.sujan.myapplication.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sujan.myapplication.R;
import com.sujan.myapplication.api.ApiService;
import com.sujan.myapplication.api.RetrofitHelper;
import com.sujan.myapplication.category.Category;
import com.sujan.myapplication.category.CategoryActivity;
import com.sujan.myapplication.category.CategoryAdapter;
import com.sujan.myapplication.category.CategoryDetailActivity;
import com.sujan.myapplication.category.Movie;
import com.sujan.myapplication.category.Result;
import com.sujan.myapplication.listener.OnClickListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ArrayList<Result> movieResult = new ArrayList<>();
    private MovieAdapter adapter;
    ApiService service = RetrofitHelper.getClient().create(ApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        initToolbar();
        initRecyclerView();
        callMovieApi();
        showMovieData();
        findViews();
    }

    private void showMovieData() {
        for (int i = 0; i < movieResult.size(); i++) {
            Log.d("Title", movieResult.get(i).getTitle());
        }
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
        adapter = new MovieAdapter(movieResult, new OnClickListener() {
            @Override
            public void onClick(int position) {
//                Category obj= categoryList.get(position);
//                Intent intent= new Intent(MovieActivity.this, CategoryDetailActivity.class);
//                intent.putExtra("id", obj.getId());
//                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void callMovieApi() {
        Call<Movie> call = service.getMovieData("ef8f48b43832a9e95b87408bf739ed51");
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movie = response.body();
                    movieResult.addAll(movie.getResults());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("Error", t.getLocalizedMessage());
            }
        });
    }

}
