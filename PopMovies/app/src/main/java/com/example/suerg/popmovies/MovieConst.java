package com.example.suerg.popmovies;

import android.net.Uri;

/**
 * Created by suerg on 6/29/2016.
 */
public class MovieConst {
    public final static String TMDB_RESULTS = "results";
    public final static String TMDB_TITLE = "title";
    public final static String TMDB_POSTER_PATH = "poster_path";
    public final static String TMDB_BACKDROP_PATH = "backdrop_path";
    public final static String TMDB_ID = "id";
    public final static String TMDB_OVERVIEW = "overview";
    public final static String TMDB_RUNTIME = "runtime";
    public final static String TMDB_VOTE_AVG = "vote_average";
    public final static String TMDB_RELEASE_DATE = "release_date";

    public Uri movieBaseUri;
    private static MovieConst ourInstance = new MovieConst();

    public static MovieConst getInstance() {
        return ourInstance;
    }

    private MovieConst() {
        Uri.Builder movieBase = new Uri.Builder();
        movieBase.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185");

        this.movieBaseUri = movieBase.build();
    }
}
