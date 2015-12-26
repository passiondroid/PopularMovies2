package com.app.popularmovies.util;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.app.popularmovies.fragment.PopularMovieFragment;
import com.app.popularmovies.fragment.TopRatedMovieFragment;
import com.app.popularmovies.model.Movie;
import com.app.popularmovies.parser.JSONParser;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by Arif on 26-Dec-15.
 */
public class CommonAsyncTask extends AsyncTask<String,Void,Object>{
    private Fragment fragment;
    private int request_type;

    public CommonAsyncTask(Fragment fragment,int request_type) {
        this.fragment = fragment;
        this.request_type = request_type;
    }

    @Override
    protected Object doInBackground(String... strings) {
        String page = strings[0];
        String sort_by = strings[1];
        List<Movie> movieList = null;

        OkHttpClient client = new OkHttpClient();
        Uri.Builder builder = Uri.parse(Constants.REQUEST_BASE_URL).buildUpon()
                .appendQueryParameter("api_key", Constants.API_KEY)
                .appendQueryParameter("page",(page==null)?"1":page)
                .appendQueryParameter("sort_by",(sort_by==null)?"":sort_by);
        Uri uri = builder.build();

        //public void run() throws Exception {
        Request request = new Request.Builder()
                .url(uri.toString())
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return null;
            }
            JSONParser parser = new JSONParser();
            String json = response.body().string();
            System.out.println(json);
            movieList = parser.parseJSON(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    @Override
    protected void onPostExecute(Object object) {
        super.onPostExecute(object);
        if(request_type==Constants.MOST_POPULAR_REQUEST)
            ((PopularMovieFragment) fragment).onTaskCompleted(object);
        else if(request_type == Constants.TOP_RATED_REQUEST)
            ((TopRatedMovieFragment)fragment).onTaskCompleted(object);
    }
}
