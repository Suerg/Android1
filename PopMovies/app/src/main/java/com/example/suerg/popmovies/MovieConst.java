package com.example.suerg.popmovies;

import android.net.Uri;

import java.net.URL;

/**
 * Created by suerg on 6/29/2016.
 */
public class MovieConst {
    public Uri movieBaseUri;
    private static MovieConst ourInstance = new MovieConst();

    public static MovieConst getInstance() {
        return ourInstance;
    }

    private MovieConst() {
        Uri.Builder builder = new Uri.Builder();
//        builder.scheme("http")
//                .authority("api.themoviedb.org")
//                .appendPath("3")
//                .appendPath("movie")
//                .appendPath("popular")
//                .appendQueryParameter("api_key", getString(R.string.moviedb_api_key));

        Uri.Builder movieBase = new Uri.Builder();
        movieBase.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185");

        this.movieBaseUri = movieBase.build();
        try {
            URL url = new URL(builder.build().toString());
        } catch (Exception ex) {}
    }
}
