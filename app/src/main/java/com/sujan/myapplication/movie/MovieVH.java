package com.sujan.myapplication.movie;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sujan.myapplication.R;

public class MovieVH extends RecyclerView.ViewHolder {
    public TextView txtTitle;
    public ImageView imgMovie;
    public TextView txtDescription;
    public TextView txtDate;

    public MovieVH(View itemView) {
        super(itemView);
        txtTitle = itemView.findViewById(R.id.txtTitle);
        imgMovie = itemView.findViewById(R.id.imgMovie);
        txtDescription = itemView.findViewById(R.id.txtDescription);
        txtDate = itemView.findViewById(R.id.txtDate);
    }
}
