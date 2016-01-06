package com.app.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Arif on 26-Dec-15.
 */
public class Movie implements Parcelable {

    private String id;
    private String name;
    private String rating;
    private String releaseDate;
    private String imageUrl;
    private String language;
    private boolean adult;
    private String overview;
    private String backdroppath;
    private String popularity;
    private String voteCount;
    private boolean video;
    private String voteAverage;

    private boolean[] booleanArray = new boolean[]{this.adult, this.video};

    public Movie(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.rating = in.readString();
        this.releaseDate = in.readString();
        this.imageUrl = in.readString();
        this.language = in.readString();
        this.overview = in.readString();
        this.backdroppath = in.readString();
        this.popularity = in.readString();
        this.voteCount = in.readString();
        this.voteAverage = in.readString();

        this.adult = booleanArray[0];
        this.video = booleanArray[1];
    }

    public Movie() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdroppath() {
        return backdroppath;
    }

    public void setBackdroppath(String backdroppath) {
        this.backdroppath = backdroppath;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.rating);
        parcel.writeString(this.releaseDate);
        parcel.writeString(this.imageUrl);
        parcel.writeString(this.language);
        parcel.writeString(this.overview);
        parcel.writeString(this.backdroppath);
        parcel.writeString(this.popularity);
        parcel.writeString(this.voteCount);
        parcel.writeString(this.voteAverage);
        parcel.writeString(this.voteAverage);
        parcel.writeBooleanArray(booleanArray);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
