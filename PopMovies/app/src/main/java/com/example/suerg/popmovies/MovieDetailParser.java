package com.example.suerg.popmovies;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by suerg on 7/1/2016.
 */
public class MovieDetailParser {
    public final String LOG_TAG = MovieDetailParser.class.getSimpleName();
    String jsonMovieDetails;

    public MovieDetailParser(String jsonMovieDetails) {
        this.jsonMovieDetails = jsonMovieDetails;
    }

    public void addDetails(Movie movie) {
        try {
            JSONObject details = new JSONObject(jsonMovieDetails);
            movie.setRuntime(details.getInt(MovieConst.TMDB_RUNTIME));
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }
    }
}
