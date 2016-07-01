package com.example.suerg.popmovies;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suerg on 6/29/2016.
 */
public class MovieGridAdapter extends ArrayAdapter<Movie> {
    private Activity context;
    private List<Movie> movies;
    Bitmap img;

    public MovieGridAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
        this.movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_grid_item, parent, false);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_grid_poster);
        Uri posterUri = movie.getPosterUri();
        Picasso.with(getContext()).load(posterUri).into(imageView);

        return convertView;
    }
}
