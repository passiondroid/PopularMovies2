package com.app.popularmovies.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.popularmovies.fragment.ReviewsFragment;
import com.app.popularmovies.fragment.OverviewFragment;
import com.app.popularmovies.fragment.TrailerFragment;
import com.app.popularmovies.model.Movie;

/**
 * Created by Azhar on 22-Aug-15.
 */
public class SwipePagerAdapter extends FragmentPagerAdapter {

    //private HoroscopeSign horoscopeSign;
    private Context mContext;
    private Movie movie;

    public SwipePagerAdapter(Context mContext, FragmentManager fm,Movie movie) {
        super(fm);
        //this.horoscopeSign = DataUtility.getInstance().getHoroscopeSign();
        this.mContext = mContext;
        this.movie = movie;
    }

    @Override
    public Fragment getItem(int position) {
        if(getPageTitle(position).equals("OVERVIEW")){
            return OverviewFragment.newInstance(movie.getOverview());
        }else if(getPageTitle(position).equals("REVIEWS")){
            return ReviewsFragment.newInstance(movie.getId());
        }else if(getPageTitle(position).equals("TRAILERS")){
            return TrailerFragment.newInstance(movie.getId());
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "OVERVIEW";
            case 1:
                return "REVIEWS";
            case 2:
                return "TRAILERS";
        }
        return null;
    }
}
