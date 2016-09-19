package com.example.android.moviedb;

/**
 * Created by pc on 9/19/2016.
 */
public class Trailer {
    String name;
    String source;

    public Trailer(String name, String source) {
        this.name = name;
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }
}
