package com.app.popularmovies.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Arif on 02-Jan-16.
 */
public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.app.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_MOVIE = "movie";

    /* Inner class that defines the table contents of the movies table */
    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "MOVIES";

        public static final String COLUMN_MOVIES_ID= "MOVIES_ID";
        public static final String COLUMN_MOVIE_NAME= "MOVIE_NAME";
        public static final String COLUMN_RATING= "RATING";
        public static final String COLUMN_RELEASE_DATE= "RELEASE_DATE";
        public static final String COLUMN_IMAGE_URL= "IMAGE_URL";
        public static final String COLUMN_LANGUAGE= "LANGUAGE";
        public static final String COLUMN_ADULT= "ADULT";
        public static final String COLUMN_OVERVIEW = "OVERVIEW";
        public static final String COLUMN_BACKDROP_PATH = "BACKDROP_PATH";
        public static final String COLUMN_POPULARITY = "POPULARITY";
        public static final String COLUMN_VOTE_COUNT = "VOTE_COUNT";
        public static final String COLUMN_VIDEO = "VIDEO";
        public static final String COLUMN_VOTE_AVERAGE = "VOTE_AVERAGE";

        public static Uri buildMoviesUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static String getMovieIdFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}
