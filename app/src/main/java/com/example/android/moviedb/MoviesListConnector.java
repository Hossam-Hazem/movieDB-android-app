package com.example.android.moviedb;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import com.example.android.moviedb.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import layout.MainFragment;

/**
 * Created by Hossam on 8/15/2016.
 */

public class MoviesListConnector extends AsyncTask<String,Void,ArrayList<MovieItem>> {
    public interface OnFinishCallback{
        void onFinished(ArrayList<MovieItem> movieItems);
    }
    final String BASEURL = "https://api.themoviedb.org/3/";
    MainFragment.MovieAdapter adapter;
    Context mContext;
    OnFinishCallback onFinishCallback;
    public MoviesListConnector(Context mContext, MainFragment.MovieAdapter adapter){
        this.adapter = adapter;
        this.mContext = mContext;
    }
    public MoviesListConnector(Context mContext, MainFragment.MovieAdapter adapter,OnFinishCallback onFinishCallback){
        this.adapter = adapter;
        this.mContext = mContext;
        this.onFinishCallback = onFinishCallback;
    }

    @Override
    protected ArrayList<MovieItem> doInBackground(String... params) {

        return getMoviesList(params[0]);

    }

    @Override
    protected void onPostExecute(ArrayList<MovieItem> movieItems) {
        super.onPostExecute(movieItems);
        adapter.clear();
        Iterator<MovieItem> i = movieItems.iterator();
        while(i.hasNext()){
            adapter.add(i.next());
        }

        onFinishCallback.onFinished(movieItems);

    }

    @Nullable
    public static ArrayList<MovieItem> getMovieDataFromJson(String moviesJSONStr, int count){
        JSONArray moviesJSON;
        ArrayList<MovieItem> movies = new ArrayList<>();
        try {
            if(moviesJSONStr != null) {
                moviesJSON = (new JSONObject(moviesJSONStr)).getJSONArray("results");
                int total;
                total = moviesJSON.length();
                JSONObject movieJSON;
                for (int c = 0; c < total; c++) {
                    movieJSON = moviesJSON.getJSONObject(c);
                    MovieItem movie = new MovieItem(
                            movieJSON.getLong("id"),
                            movieJSON.getString("title"),
                            movieJSON.getString("release_date"),
                            (float) movieJSON.getDouble("vote_average"),
                            movieJSON.getString("overview"),
                            movieJSON.getString("poster_path"));
                    movies.add(movie);
                }
            }

            return movies;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public ArrayList<MovieItem> getMoviesList(String ACTION){
        if(ACTION.equals("favorites")){
            return getMoviesListFromDB(ACTION);
        }
        else{
            return getMoviesListFromWeb(ACTION);
        }

    }

    private ArrayList<MovieItem> getMoviesListFromDB(String ACTION) {
        return MovieContract.FavoriteEntry.getFavorites(mContext);
    }

    private ArrayList<MovieItem> getMoviesListFromWeb(String ACTION){
        final String API_KEY = MyConfig.MOVIEDB_API_KEY;
        final String API_PARAM = "api_key";
        Uri uri =  Uri.parse(BASEURL+ACTION).buildUpon()
                .appendQueryParameter(API_PARAM,API_KEY)
                .build();

        String JSONStr = MyConnection.connect(uri.toString());

        return getMovieDataFromJson(JSONStr,7);
    }

}
