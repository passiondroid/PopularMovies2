package com.app.popularmovies.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.app.popularmovies.adapter.TrailerFragmentAdapter;
import com.app.popularmovies.interfaces.onTaskCompleted;
import com.app.popularmovies.model.Trailer;
import com.app.popularmovies.util.CommonAsyncTask;
import com.app.popularmovies.util.Constants;

import java.util.ArrayList;
import java.util.List;


public class TrailerFragment extends ListFragment implements onTaskCompleted {

    public static final String MOVIE_ID = "movie_id";
    private String mParam;
    private List<Trailer> trailerList = new ArrayList<>();
    private TrailerFragmentAdapter adapter;

    public static TrailerFragment newInstance(String param) {
        TrailerFragment fragment = new TrailerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_ID,param);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrailerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //mParam = getArguments().getString(MOVIE_ID);
            CommonAsyncTask asyncTask = new CommonAsyncTask(this, Constants.VIDEO_TRAILER_REQUEST);
            asyncTask.execute();
        }

        adapter = new TrailerFragmentAdapter(getActivity(),trailerList);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Trailer trailer = trailerList.get(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailer.getKey()));
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }else {
                Intent ytIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOU_TUBE_BASE_URL + trailer.getKey()));
                startActivity(ytIntent);
            }
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
            trailerList.addAll((List<Trailer>) object);
            adapter.notifyDataSetChanged();
            if(trailerList.size()==0){
                setEmptyText("No Trailers Found");
            }else{
                ((DetailFragment)getParentFragment()).setShareIntent("Watch the trailer - "+Constants.YOU_TUBE_BASE_URL + trailerList.get(0).getKey());
            }
        }else{
            Toast.makeText(getActivity(), "Sorry, some error occured", Toast.LENGTH_SHORT).show();
        }
    }





}
