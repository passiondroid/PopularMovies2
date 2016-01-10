package com.app.popularmovies.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.app.popularmovies.adapter.ReviewFragmentAdapter;
import com.app.popularmovies.adapter.TrailerFragmentAdapter;
import com.app.popularmovies.interfaces.onTaskCompleted;
import com.app.popularmovies.model.Review;
import com.app.popularmovies.model.Trailer;
import com.app.popularmovies.util.CommonAsyncTask;
import com.app.popularmovies.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class ReviewsFragment extends ListFragment implements onTaskCompleted {

    public static final String MOVIE_ID = "movie_id";
    private String mParam;
    private List<Review> reviewList = new ArrayList<>();
    private ReviewFragmentAdapter adapter;

    public static ReviewsFragment newInstance(String param) {
        ReviewsFragment fragment = new ReviewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_ID,param);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReviewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(MOVIE_ID);
            CommonAsyncTask asyncTask = new CommonAsyncTask(this, Constants.MOVIE_REVIEWS_REQUEST);
            asyncTask.execute();
        }

        adapter = new ReviewFragmentAdapter(getActivity(),reviewList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public void onStart() {
        super.onStart();
        getListView().setDivider(new ColorDrawable(Color.BLACK));
        getListView().setDividerHeight(1);
    }

    @Override
    public void onTaskCompleted(Object object) {
        if(null!=object) {
            reviewList.addAll((List<Review>) object);
            adapter.notifyDataSetChanged();
            if(reviewList.size()==0){
                setEmptyText("No Reviews Found");
            }
        }else{
            Toast.makeText(getActivity(), "Sorry, some error occured", Toast.LENGTH_SHORT).show();
        }
    }

}
