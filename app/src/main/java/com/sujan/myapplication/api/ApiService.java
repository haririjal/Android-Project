package com.sujan.myapplication.api;

import com.sujan.myapplication.category.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/upcoming")
    Call<Movie> getMovieData(
            @Query("api_key") String apiKey
    );

}
