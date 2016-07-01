package com.example.suerg.popmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by suerg on 6/27/2016.
 */
public class MovieListParser {
    String jsonMovieList;
    private final String LOG_TAG = MovieListParser.class.getSimpleName();

    public MovieListParser(String jsonMovieList) {
        this.jsonMovieList = jsonMovieList;
    }

    protected JSONArray getList() {
        try {
            JSONArray list = new JSONObject(jsonMovieList).getJSONArray(MovieConst.TMDB_RESULTS);
            return list;
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.toString());
        }
        return null;
    }

    public ArrayList<Movie> getMovies() {
        ArrayList<Movie> movieList = new ArrayList<>();
        JSONArray list = getList();
        int len = list.length();

        for (int i = 0; i < len; i++) {
            try {
                JSONObject currentMovie = list.getJSONObject(i);
                String poster_path = currentMovie.getString(MovieConst.TMDB_POSTER_PATH);
                String backdrop_path = currentMovie.getString(MovieConst.TMDB_BACKDROP_PATH);
                StringBuilder poster_builder = new StringBuilder(poster_path);
                StringBuilder backdrop_builder = new StringBuilder(backdrop_path);
                poster_builder.deleteCharAt(0);
                backdrop_builder.deleteCharAt(0);

                poster_path = poster_builder.toString();
                backdrop_path = backdrop_builder.toString();

                Movie movie = new Movie(
                                               currentMovie.getString(MovieConst.TMDB_TITLE),
                                               backdrop_path,
                                               poster_path,
                                               currentMovie.getString(MovieConst.TMDB_ID),
                                               currentMovie.getString(MovieConst.TMDB_OVERVIEW),
                                               currentMovie.getDouble(MovieConst.TMDB_VOTE_AVG),
                                               currentMovie.getString(MovieConst.TMDB_RELEASE_DATE)
                );
                movieList.add(movie);
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.toString());
            }
        }

        return movieList;
    }

}
