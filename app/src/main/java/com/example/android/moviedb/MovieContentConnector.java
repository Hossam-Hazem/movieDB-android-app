package com.example.android.moviedb;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import layout.MovieDetailFragment;
import layout.MovieTrailersFragment;

/**
 * Created by pc on 9/19/2016.
 */
public class MovieContentConnector extends AsyncTask<String,Void,MovieContentConnector.MovieContentPackage> {
    final String BASEURL = "https://api.themoviedb.org/3/";
    ArrayList<Trailer> trailersAdapter;
    ArrayList<Review> reviewsAdapter;
    final String API_KEY = MyConfig.MOVIEDB_API_KEY;
    final String API_PARAM = "api_key";

    public MovieContentConnector(ArrayList<Trailer> trailersAdapter, ArrayList<Review> reviewsAdapter) {
        this.trailersAdapter = trailersAdapter;
        this.reviewsAdapter = reviewsAdapter;
    }

    public ArrayList<Trailer> getMovieTrailers(String id){
        Uri uri = appendMovieId(id).buildUpon()
                .appendPath("trailers")
                .build();
        String JSONStr = MyConnection.connect(uri.toString());

        return getTrailersFromJson(JSONStr);
    }

    public ArrayList<Trailer> getTrailersFromJson(String JSONStr){
        JSONArray trailersJSON;
        ArrayList<Trailer> trailers = new ArrayList<>();
        try {
            if(JSONStr != null) {
                trailersJSON = (new JSONObject(JSONStr)).getJSONArray("youtube");
                for (int c = 0; c < trailersJSON.length(); c++) {
                    JSONObject o = trailersJSON.getJSONObject(c);
                    String name = o.getString("name");
                    String source = o.getString("source");
                    Trailer t = new Trailer(name, source);
                    trailers.add(t);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }

    public ArrayList<Review> getMovieReviews(String id){
        Uri uri = appendMovieId(id).buildUpon()
                .appendPath("reviews")
                .build();
        String JSONStr = MyConnection.connect(uri.toString());

        return getReviewsFromJson(JSONStr);
    }

    private ArrayList<Review> getReviewsFromJson(String JSONStr) {
        JSONArray reviewsJSON;
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            if(JSONStr != null) {
                reviewsJSON = (new JSONObject(JSONStr)).getJSONArray("results");
                for (int c = 0; c < reviewsJSON.length(); c++) {
                    JSONObject o = reviewsJSON.getJSONObject(c);
                    String author = o.getString("author");
                    String content = o.getString("content");
                    Review t = new Review(author, content);
                    reviews.add(t);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public Uri appendMovieId(String id){
        return appendApiKey(Uri.parse(BASEURL).buildUpon()
                .appendPath("movie")
                .appendPath(id)
                .build());
    }

    @Override
    protected void onPostExecute(MovieContentPackage movieContentPackage) {
        super.onPostExecute(movieContentPackage);

        addTrailersToAdapter(movieContentPackage.trailers);
        addReviewsToAdapter(movieContentPackage.reviews);
    }

    private void addTrailersToAdapter(ArrayList<Trailer> trailers){
        Iterator<Trailer> i = trailers.iterator();
        while(i.hasNext()){
            trailersAdapter.add(i.next());
        }
    }

    public void addReviewsToAdapter(ArrayList<Review> reviews){
        Iterator<Review> i = reviews.iterator();
        while(i.hasNext()){
            reviewsAdapter.add(i.next());
        }
    }

    @Override
    protected MovieContentPackage doInBackground(String... params) {
        return new MovieContentPackage(
                getMovieReviews(params[0]),
                getMovieTrailers(params[0])
        );
    }

    public class MovieContentPackage{
        ArrayList<Review> reviews;
        ArrayList<Trailer> trailers;

        public MovieContentPackage(ArrayList<Review> reviews, ArrayList<Trailer> trailers) {
            this.reviews = reviews;
            this.trailers = trailers;
        }
    }
    public Uri appendApiKey(Uri uri){
        return uri.buildUpon().appendQueryParameter(API_PARAM,API_KEY).build();
    }
}
