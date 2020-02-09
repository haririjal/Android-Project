package com.sujan.myapplication.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.sujan.myapplication.R;
import com.sujan.myapplication.category.Result;
import com.sujan.myapplication.listener.OnClickListener;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieVH> {
    private ArrayList<Result> movieResult = new ArrayList<>();
    private OnClickListener listener;

    public MovieAdapter(ArrayList<Result> movieResult, final OnClickListener listener) {
        this.movieResult.addAll(movieResult);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movie_item, parent, false);
        return new MovieVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVH holder, final int position) {
        Result movieResult = this.movieResult.get(position);
        Picasso.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + movieResult.getPosterPath())
                .into(holder.imgMovie);
        holder.txtTitle.setText(movieResult.getTitle());
        holder.txtDescription.setText(movieResult.getOverview());
        holder.txtDate.setText(movieResult.getReleaseDate());

    }

    @Override
    public int getItemCount() {
        return movieResult.size();
    }
}
