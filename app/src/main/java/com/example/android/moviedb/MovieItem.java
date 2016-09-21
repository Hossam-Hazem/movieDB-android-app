package com.example.android.moviedb;

import android.content.Context;

import com.example.android.moviedb.data.MovieContract;

import java.io.Serializable;

/**
 * Created by Hossam on 8/15/2016.
 */

public class MovieItem implements Serializable {
    private long id;
    private String name;
    private String date;
    private float rating;
    private String description;
    private String imagePath;
    private String baseURI;
    private String backDropURI;

    public MovieItem(long id, String name, String date, float rating, String description, String imagePath) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.rating = rating;
        this.description = description;
        this.imagePath = imagePath;
        this.baseURI = "https://image.tmdb.org/t/p/w300";
        this.backDropURI = "https://image.tmdb.org/t/p/w780";
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public float getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageURL(){
        return baseURI+imagePath;
    }

    public String getImageBackDropURL(){
        return backDropURI+imagePath;
    }

    public boolean isFavorite(Context context){
        return MovieContract.FavoriteEntry.checkMovieExistsByName(context,id);
    }

    public boolean setFavorite(Context context){
        return MovieContract.FavoriteEntry.insert(
                context,
                this
        );
    }
    public boolean removeFavorite(Context context){
        return MovieContract.FavoriteEntry.delete(
                context,
                this.id
        );
    }
}
