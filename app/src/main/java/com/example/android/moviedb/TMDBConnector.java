package com.example.android.moviedb;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import layout.MainFragment;

/**
 * Created by Hossam on 8/15/2016.
 */

public class TMDBConnector extends AsyncTask<String,Void,ArrayList<MovieItem>> {
    final String LOG_TAG = TMDBConnector.class.getSimpleName();
    MainFragment.MovieAdapter adapter;
    public TMDBConnector(MainFragment.MovieAdapter adapter){
        this.adapter = adapter;
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

    }

    public static ArrayList<MovieItem> getMovieDataFromJson(String moviesJSONStr,int count){
        JSONArray moviesJSON;
        ArrayList<MovieItem> movies = new ArrayList<>();
        try {
            moviesJSON  = (new JSONObject(moviesJSONStr)).getJSONArray("results");
            int total;
            if(count == -1)
                total = moviesJSON.length();
            else
                total = count;
            JSONObject movieJSON;
            for(int c = 0;c<total;c++){
                movieJSON = moviesJSON.getJSONObject(c);
                MovieItem movie = new MovieItem(movieJSON.getString("title"),
                        movieJSON.getString("release_date"),
                        (float)movieJSON.getDouble("vote_average"),
                        movieJSON.getString("overview"),
                        movieJSON.getString("poster_path"));
                movies.add(movie);
            }

            return movies;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String connect(String uriString){
        String resultJSONString;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        URL url = null;
        InputStream inputStream = null;
        try {
            url = new URL(uriString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // Create the request to OpenWeatherMap, and open the connection
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
        }
        catch(Exception e){
            e.printStackTrace();
            if (urlConnection != null) {
                // urlConnection.disconnect();
            }
        }

        // Read the input stream into a String

        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            // Nothing to do.
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        if (buffer.length() == 0) {
            // Stream was empty.  No point in parsing.
            return null;
        }
        resultJSONString = buffer.toString();
        Log.v(LOG_TAG,"Forecast JSON String: "+ resultJSONString);

        if (urlConnection != null) {
             urlConnection.disconnect();
        }

        return resultJSONString;
    }

    public ArrayList<MovieItem> getMoviesList(String ACTION){
        final String BASEURL = "https://api.themoviedb.org/3/";
        final String API_KEY = BuildConfig.MOVIEDB_API_KEY;
        final String API_PARAM = "api_key";
        Uri uri =  Uri.parse(BASEURL+ACTION).buildUpon()
                .appendQueryParameter(API_PARAM,API_KEY)
                .build();

        String JSONStr = connect(uri.toString());

        return getMovieDataFromJson(JSONStr,7);
    }
}
