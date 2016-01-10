package com.app.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.app.popularmovies.R;
import com.app.popularmovies.fragment.DetailFragment;
import com.app.popularmovies.model.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null && null != getIntent()) {
            Movie movie = getIntent().getParcelableExtra("movie");
            DetailFragment detailFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("movie", movie);
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detailContainer,detailFragment)
                    .commit();
        }
    }

}
