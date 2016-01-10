package com.app.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.popularmovies.R;
import com.app.popularmovies.model.Movie;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Arif on 26-Dec-15.
 */
public class PopularMovieGridAdapter extends BaseAdapter {


    private final Context mContext;
    private final List<Movie> movieList;

    public PopularMovieGridAdapter(Context context,List<Movie> movieList) {
        this.mContext = context;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int i) {
        return movieList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(R.layout.popular_movie_grid_item, parent, false);
            holder = new ViewHolder();
            holder.movieIV = (ImageView) convertView.findViewById(R.id.movieIV);
            holder.movieNameTV = (TextView) convertView.findViewById(R.id.movieNameTV);
            holder.movieRatingTV = (TextView) convertView.findViewById(R.id.movieRatingTV);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.movieNameTV.setText(movieList.get(i).getName());
        int x = getRoundedNumber(movieList.get(i).getVoteAverage());
        holder.movieRatingTV.setText(x+"/10");

        Glide.with(mContext)
                .load(movieList.get(i).getImageUrl())
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(holder.movieIV);

        return convertView;
    }

    static class ViewHolder{
        ImageView movieIV;
        TextView movieNameTV, movieRatingTV;

    }

    public int getRoundedNumber(String number){
        float value = Float.parseFloat(number);
        return Math.round(value);
    }

}
