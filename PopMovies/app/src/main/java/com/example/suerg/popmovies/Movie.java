package com.example.suerg.popmovies;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by suerg on 6/29/2016.
 */
public class Movie implements Parcelable {
    String title;
    private String backdrop_path;
    private String poster_path;
    String id;
    String overview;
    double vote_avg;
    String release_date;
    int runtime;

    public Movie(String title, String backdrop_path, String poster_path, String id,
                 String overview, double vote_avg, String release_date) {
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.poster_path = poster_path;
        this.id = id;
        this.overview = overview;
        this.vote_avg = vote_avg;
        this.release_date = release_date;
    }

    public Movie(Parcel in) {
        this.title = in.readString();
        this.backdrop_path = in.readString();
        this.poster_path = in.readString();
        this.id = in.readString();
        this.overview = in.readString();
        this.vote_avg = in.readDouble();
        this.release_date = in.readString();
        this.runtime = in.readInt();
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(backdrop_path);
        dest.writeString(poster_path);
        dest.writeString(id);
        dest.writeString(overview);
        dest.writeDouble(vote_avg);
        dest.writeString(release_date);
        dest.writeInt(runtime);
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

    @Override
    public int describeContents() {
        return 0;
    }

    static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
