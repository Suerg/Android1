package com.example.suerg.popmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    Movie mMovie;
    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        mMovie = getActivity().getIntent().getExtras().getParcelable("DETAILS");

        TextView title = (TextView) rootView.findViewById(R.id.textview_movie_detail_title);
        title.setText(mMovie.title);

        TextView year = (TextView) rootView.findViewById(R.id.textview_movie_detail_year);
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyy");
            Date releaseDate = formater.parse(mMovie.release_date);
            year.setText(formater.format(releaseDate));
        } catch (Exception ex) {}

        TextView rating = (TextView) rootView.findViewById(R.id.textview_movie_detail_rating);
        rating.setText(mMovie.vote_avg + "/10");

        final ImageView imageView = (ImageView) rootView.findViewById(R.id.imageview_poster_movie_detail);
        Uri posterUri = mMovie.getPosterUri();
        Picasso.with(getContext()).load(posterUri).into(imageView);

        new GetMovieDetails().execute(mMovie.id);

        return rootView;
    }

    public class GetMovieDetails extends AsyncTask<String, Void, Void> {

        private final String LOG_TAG = GetMovieDetails.class.getSimpleName();

        protected String getApiKey() {

            return "";
        }

        protected Void doInBackground(String... ids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String result;
            ArrayList<Movie> movieNames;

            try {
//            http://api.themoviedb.org/3/movie/popular?api_key=135f10fae785ba02b00aece950e9cad2
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("movie")
                        .appendPath(ids[0])
                        .appendQueryParameter("api_key", getString(R.string.moviedb_api_key));

                URL url = new URL(builder.build().toString());


                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                result = buffer.toString();
                Log.v(LOG_TAG, result);
                new MovieDetailParser(result).addDetails(mMovie);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        protected void onPostExecute(Void nothing) {
            TextView runtime = (TextView) getView().findViewById(R.id.textview_movie_detail_runtime);
            runtime.setText(mMovie.runtime + "min");
        }
    }
}

