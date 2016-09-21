package com.example.android.moviedb;

import java.io.Serializable;

/**
 * Created by pc on 9/19/2016.
 */
public class Trailer implements Serializable {
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

    public String getURL(){
        return "http://www.youtube.com/watch?v="+source;
    }
}
