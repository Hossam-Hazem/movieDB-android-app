package com.example.android.moviedb;

/**
 * Created by Hossam on 9/19/2016.
 */
public class Review {
    String author;
    String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
