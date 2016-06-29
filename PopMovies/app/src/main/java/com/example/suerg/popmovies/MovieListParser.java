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

    private final String TMDB_RESULTS = "results";
    private final String TMDB_TITLE = "title";
    private final String TMDB_POSTER_PATH = "poster_path";
    private final String TMDB_BACKDROP_PATH = "backdrop_path";
    private final String TMDB_ID = "id";

    public MovieListParser(String jsonMovieList) {
        this.jsonMovieList = jsonMovieList;
    }

    protected JSONArray getList() {
        try {
            JSONArray list = new JSONObject(jsonMovieList).getJSONArray(TMDB_RESULTS);
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
                String poster_path = list.getJSONObject(i).getString(TMDB_POSTER_PATH);
                String backdrop_path = list.getJSONObject(i).getString(TMDB_BACKDROP_PATH);
                StringBuilder poster_builder = new StringBuilder(poster_path);
                StringBuilder backdrop_builder = new StringBuilder(backdrop_path);
                poster_builder.deleteCharAt(0);
                backdrop_builder.deleteCharAt(0);

                poster_path = poster_builder.toString();
                backdrop_path = backdrop_builder.toString();
                Movie movie = new Movie(
                    list.getJSONObject(i).getString(TMDB_TITLE),
                    backdrop_path,
                    poster_path,
                    list.getJSONObject(i).getString(TMDB_ID)
                );
                movieList.add(movie);
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.toString());
            }
        }

        return movieList;
    }

}
