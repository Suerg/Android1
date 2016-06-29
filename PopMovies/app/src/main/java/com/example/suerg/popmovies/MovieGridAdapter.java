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
//        new DownloadImageTask().execute(posterUri.toString());
//        imageView.setImageBitmap(img);
        Picasso.with(getContext()).setLoggingEnabled(true);
        Picasso.with(getContext()).load(posterUri).into(imageView);
//        Picasso.with(context).load(posterUri).into(new Target() {
//            @Override
//            public void onPrepareLoad(Drawable arg0) {
////                imageView.setImageDrawable(arg0);
//            }
//
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom arg1) {
////                Drawable drawImage = new BitmapDrawable(ccView.getRootView().getContext().getApplicationContext().getResources(),bitmap);
//                imageView.setImageBitmap(bitmap);
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable arg0) {
//
//            }
//
//        });

//        TextView textView = (TextView) convertView.findViewById(R.id.movie_title);
//        textView.setText(movie.title);

//        iconView.setImageResource(androidFlavor.image);
//
//        TextView versionNameView = (TextView) convertView.findViewById(R.id.list_item_version_name);
//        versionNameView.setText(androidFlavor.versionName);
//
//        TextView versionNumberView = (TextView) convertView.findViewById(R.id.list_item_versionnumber_textview);
//        versionNumberView.setText(androidFlavor.versionNumber);
        return convertView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        public DownloadImageTask() {
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            img = result;
        }
    }
}
