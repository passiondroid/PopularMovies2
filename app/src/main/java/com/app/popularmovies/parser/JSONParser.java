package com.app.popularmovies.parser;

import com.app.popularmovies.model.Movie;
import com.app.popularmovies.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arif on 26-Dec-15.
 */
public class JSONParser {

   public List<Movie> parseJSON(String json){
       List<Movie> movieList = new ArrayList<>();
       try {
           JSONObject jsonObject = new JSONObject(json);
           JSONArray jsonArray = jsonObject.getJSONArray("results");
           for(int i =0;i<jsonArray.length();i++){
               Movie movie = new Movie();
               JSONObject object = jsonArray.getJSONObject(i);
               movie.setImageUrl(Constants.IMAGE_PATH_BASE_URL + object.get("poster_path"));
               movie.setAdult(object.getBoolean("adult"));
               movie.setOverview(object.getString("overview"));
               movie.setReleaseDate(object.getString("release_date"));
               movie.setName(object.getString("original_title"));
               movie.setLanguage(object.getString("original_language"));
               movie.setBackdroppath(Constants.BACKDROP_IMAGE_PATH_BASE_URL + object.getString("backdrop_path"));
               movie.setPopularity(object.getString("popularity"));
               movie.setVoteCount(object.getString("vote_count"));
               movie.setVideo(object.getBoolean("video"));
               movie.setVoteAverage(object.getString("vote_average"));
               movieList.add(movie);
           }

       } catch (JSONException e) {
           e.printStackTrace();
       }

       return movieList;
   }
}
