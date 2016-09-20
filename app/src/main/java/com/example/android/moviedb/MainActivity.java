package com.example.android.moviedb;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import layout.MainFragment;
import layout.MovieDetailFragment;

public class MainActivity extends MovieParentActivity implements MainFragment.TwoPaneInterface {

    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new MainFragment())
//                    .commit();
//        }

        if(findViewById(R.id.movie_detail_container)!=null){
            mTwoPane = true;
            if(savedInstanceState == null){
                MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                Bundle bundle = new Bundle();
               // bundle.putSerializable("movieDetails",movieItem);
                bundle.putBoolean("twoPane",true);
                movieDetailFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.movie_detail_container,movieDetailFragment)
                        .commit();
            }

        }
        else{
            mTwoPane = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Class intentClass;

        switch (id){
            case R.id.action_settings:
                intentClass = MainSettingsActivity.class;
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        startActivity(new Intent(this,intentClass));
        return true;
    }


    @Override
    public void listItemClickCallback(MovieItem movieItem) {
        if(mTwoPane){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, MovieDetailFragment.newFragmentWithBundle(movieItem,true))
                    .addToBackStack(null)
                    .commit();
        }
        else{
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.MOVIE_SERIALIZABLE_KEY, movieItem);

            startActivity(intent);
        }
    }

    public void removeMovieFromFavorites(MovieItem item){
        MainFragment fragment = (MainFragment) this.getSupportFragmentManager().findFragmentById(R.id.movies_list_fragment);
        fragment.removeFavoriteFromAdapter(item);
    }
    public void addMovieToFavorites(MovieItem item){
        MainFragment fragment = (MainFragment) this.getSupportFragmentManager().findFragmentById(R.id.movies_list_fragment);
        fragment.addFavoriteToAdapter(item);
    }

}
