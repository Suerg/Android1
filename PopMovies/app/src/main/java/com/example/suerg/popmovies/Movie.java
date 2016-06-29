package com.example.suerg.popmovies;

import android.net.Uri;


/**
 * Created by suerg on 6/29/2016.
 */
public class Movie {
    String title;
    private String backdrop_path;
    private String poster_path;
    String id;

    public Movie(String title, String backdrop_path, String poster_path, String id) {
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.id = id;
    }

    public Uri getImageUri(String image_path) {
        Uri.Builder builder = MovieConst.getInstance().movieBaseUri.buildUpon();
        builder.appendPath(image_path);
        try {
            return builder.build();
        } catch (Exception ex) {}
        return null;
    }

    public Uri getPosterUri() {
        return getImageUri(poster_path);
    }

    public Uri getBackdropUri() {
        return getImageUri(backdrop_path);
    }
}
