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

    public ArrayList<String> getMovies() {
        ArrayList<String> movieList = new ArrayList<String>();
        JSONArray list = getList();
        int len = list.length();

        for (int i = 0; i < len; i++) {
            try {
                movieList.add(
                    list.getJSONObject(i).getString(TMDB_TITLE)
                );
            } catch (Exception ex) {
                Log.e(LOG_TAG, ex.toString());
            }
        }

        return movieList;
    }

}
