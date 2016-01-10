package com.app.popularmovies.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.app.popularmovies.R;
import com.app.popularmovies.activity.DetailActivity;
import com.app.popularmovies.activity.MovieActivity;
import com.app.popularmovies.adapter.PopularMovieGridAdapter;
import com.app.popularmovies.interfaces.onTaskCompleted;
import com.app.popularmovies.model.Movie;
import com.app.popularmovies.util.CommonAsyncTask;
import com.app.popularmovies.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class PopularMovieFragment extends Fragment implements onTaskCompleted, AdapterView.OnItemClickListener {

    private List<Movie> movieList = new ArrayList<>();
    private PopularMovieGridAdapter adapter;
    private GridView gridView;
    private int pageCount=1;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean itemClicked = false;

    public static PopularMovieFragment newInstance() {
        PopularMovieFragment fragment = new PopularMovieFragment();
        return fragment;
    }

    public PopularMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState !=null){
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
        //setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView)rootView.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public int currentScrollState;
            public int currentFirstVisibleItem;
            public int currentVisibleItemCount;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    this.currentFirstVisibleItem = firstVisibleItem;
                    this.currentVisibleItemCount = visibleItemCount;
                }
            }


            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            private void isScrollCompleted() {
                if (this.currentVisibleItemCount > 0 && this.currentScrollState == SCROLL_STATE_IDLE) {
                    pageCount++;
                    CommonAsyncTask asyncTask = new CommonAsyncTask(PopularMovieFragment.this, Constants.MOST_POPULAR_REQUEST);
                    asyncTask.execute(pageCount + "");
                }
            }
        });
        adapter = new PopularMovieGridAdapter(getActivity(), movieList);
        gridView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation) {
            gridView.setNumColumns(3);
        }else{
            gridView.setNumColumns(2);
        }
        if(movieList==null || movieList.size()==0) {
            CommonAsyncTask asyncTask = new CommonAsyncTask(this, Constants.MOST_POPULAR_REQUEST);
            asyncTask.execute(pageCount + "");
        }
        else if(movieList!=null && movieList.size()>0 && ((MovieActivity)getActivity()).mTwoPane) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detailContainer, DetailFragment.newInstance(movieList.get(0)), DETAILFRAGMENT_TAG)
                    .commit();
            itemClicked = true;
        }
        getActivity().setTitle("Popular Movies");

    }


    @Override
    public void onTaskCompleted(Object object) {
        if(null!=object) {
            movieList.addAll((List<Movie>) object);
            adapter.notifyDataSetChanged();
            if(((MovieActivity)getActivity()).mTwoPane && !itemClicked) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detailContainer, DetailFragment.newInstance(movieList.get(0)), DETAILFRAGMENT_TAG)
                        .commit();
                itemClicked = true;
            }
        }else{
            Toast.makeText(getActivity(), "Sorry, some error occured", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(((MovieActivity)getActivity()).mTwoPane) {
            DetailFragment detailFragment = DetailFragment.newInstance(movieList.get(i));
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detailContainer, detailFragment, DETAILFRAGMENT_TAG)
                            .commit();
        }else {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("movie", movieList.get(i));
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", (ArrayList<? extends Parcelable>) movieList);
    }

}