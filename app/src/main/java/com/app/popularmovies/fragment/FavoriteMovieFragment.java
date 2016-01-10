package com.app.popularmovies.fragment;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.popularmovies.R;
import com.app.popularmovies.activity.DetailActivity;
import com.app.popularmovies.activity.MovieActivity;
import com.app.popularmovies.adapter.PopularMovieGridAdapter;
import com.app.popularmovies.database.MoviesContract;
import com.app.popularmovies.interfaces.onTaskCompleted;
import com.app.popularmovies.model.Movie;
import com.app.popularmovies.util.CommonAsyncTask;
import com.app.popularmovies.util.Constants;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavoriteMovieFragment extends Fragment implements AdapterView.OnItemClickListener  {

    private List<Movie> movieList= new ArrayList<>();
    private PopularMovieGridAdapter adapter;
    private GridView gridView;
    private TextView emptyTextView;
    private int pageCount=1;
    private boolean itemClicked = false;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FavoriteMovieFragment newInstance() {
        FavoriteMovieFragment fragment = new FavoriteMovieFragment();
        return fragment;
    }

    public FavoriteMovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState !=null){
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
        getActivity().setTitle("Favorites");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        emptyTextView = (TextView)rootView.findViewById(R.id.EmptyTextView);
        gridView = (GridView)rootView.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        adapter = new PopularMovieGridAdapter(getActivity(), movieList);
        gridView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation){
            gridView.setNumColumns(3);
        }else{
            gridView.setNumColumns(2);
        }
        if(movieList==null || movieList.size()==0 ) {
            Cursor cursor = getActivity().getContentResolver().query(MoviesContract.MovieEntry.CONTENT_URI, null, null, null, null);
            getMovieListFromCursor(cursor);
            if(movieList.size()==0){
                emptyTextView.setText("No Favorites Added");
                emptyTextView.setVisibility(View.VISIBLE);
                if(((MovieActivity)getActivity()).mTwoPane){
                    Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(fragment)
                            .commit();
                }
            }
            else{
                adapter.notifyDataSetChanged();
                if(((MovieActivity)getActivity()).mTwoPane){
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detailContainer, DetailFragment.newInstance(movieList.get(0)), DETAILFRAGMENT_TAG)
                            .commit();
                }
            }
        }
    }


    private void getMovieListFromCursor(Cursor cursor) {
        while(cursor.moveToNext()){
            Movie movie = new Movie();
            movie.setName(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_NAME)));
            movie.setId(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIES_ID)));
            movie.setRating(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RATING)));
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_RELEASE_DATE)));
            movie.setImageUrl(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_IMAGE_URL)));
            movie.setLanguage(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_LANGUAGE)));
            movie.setAdult(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_ADULT)) > 0);
            movie.setOverview(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_OVERVIEW)));
            movie.setBackdroppath(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_BACKDROP_PATH)));
            movie.setPopularity(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POPULARITY)));
            movie.setVoteCount(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_COUNT)));
            movie.setVideo(cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VIDEO)) > 0);
            movie.setVoteAverage(cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_VOTE_AVERAGE)));
            movieList.add(movie);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(((MovieActivity)getActivity()).mTwoPane) {
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie",movieList.get(i));
            bundle.putString("fragmenttype", Constants.FRAGMENT_TYPE_FAVORITE);
            detailFragment.setArguments(bundle);
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