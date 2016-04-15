package com.app.popularmovies.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.popularmovies.R;
import com.app.popularmovies.fragment.DetailFragment;
import com.app.popularmovies.fragment.FavoriteMovieFragment;
import com.app.popularmovies.fragment.PopularMovieFragment;
import com.app.popularmovies.fragment.TopRatedMovieFragment;
import com.app.popularmovies.util.CommonAsyncTask;
import com.app.popularmovies.util.Constants;

public class MovieActivity extends AppCompatActivity {

    /*SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;*/
    public boolean mTwoPane;
    Fragment movieFragment;
    String[] items = {"Most Popular","Top Rated","Favorites"};
    private int itemSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (findViewById(R.id.detailContainer) != null) {
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, PopularMovieFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_movie_fragment,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_sort){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(itemSelected!=which) {
                        itemSelected = which;
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, getFragmentType(itemSelected))
                                .addToBackStack(null)
                                .commit();
                    }
                }
            }).create().show();
        }
        return true;
    }

    private Fragment getFragmentType(int itemSelected){
        switch(itemSelected){
            case 0:
                return PopularMovieFragment.newInstance();
            case 1:
                return TopRatedMovieFragment.newInstance();
            case 2:
                return FavoriteMovieFragment.newInstance();
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        itemSelected = -1;
    }
}
