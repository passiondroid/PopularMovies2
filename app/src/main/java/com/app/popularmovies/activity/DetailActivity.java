package com.app.popularmovies.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.popularmovies.R;
import com.app.popularmovies.adapter.SwipePagerAdapter;
import com.app.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView backDropIV,posterIV;
    private TextView nameTV,releaseDateTV,ratingTV,popularityTV,overviewTV;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_detail);
        setContentView(R.layout.fragment_detail);
        backDropIV = (ImageView)findViewById(R.id.backdropIV);
        posterIV = (ImageView)findViewById(R.id.imageIV);
        nameTV = (TextView)findViewById(R.id.nameTV);
        releaseDateTV = (TextView)findViewById(R.id.relaseDateTV);
        ratingTV = (TextView)findViewById(R.id.ratingTV);
        //popularityTV = (TextView)findViewById(R.id.popularityTV);
        //overviewTV = (TextView)findViewById(R.id.overviewTV);
        viewPager = (ViewPager)findViewById(R.id.pager);
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("movie");
        SwipePagerAdapter adapter = new SwipePagerAdapter(this,getSupportFragmentManager(),movie);
        viewPager.setAdapter(adapter);

        Picasso.with(this)
                .load(movie.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(posterIV);

        nameTV.setText(movie.getName());
        releaseDateTV.setText("Release Date : "+movie.getReleaseDate());
        ratingTV.setText(movie.getVoteAverage()+ " stars and "+movie.getVoteCount()+ " votes");
        //popularityTV.setText("Popularity : "+getRoundedPopularity(movie.getPopularity()));
        /*if(movie.getOverview()!=null && !movie.getOverview().equals("")){
            overviewTV.setText(movie.getOverview());
        }else{
            overviewTV.setText("No summary present");
        }*/
    }

    private String getRoundedPopularity(String popularity){
        double pop = Double.parseDouble(popularity);
        int x = (int)Math.round(pop);
        return x+"";

    }

}
