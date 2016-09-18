package com.example.android.moviedb;

import android.content.Intent;
import android.os.Bundle;

import layout.MovieDetailFragment;

public class MovieDetailActivity extends MovieParentActivity {

    public final static String MOVIE_SERIALIZABLE_KEY = "movieDetails";

    public MovieItem movieItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);



        Intent intent = getIntent();
        movieItem = (MovieItem) intent.getSerializableExtra(MOVIE_SERIALIZABLE_KEY);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, MovieDetailFragment.newFragmentWithBundle(movieItem,false))
                    .commit();
        }

    }









}
