package com.example.michelle.watchlist;

/**
 * Created by Michelle on 15-11-2016.
 * Class that holds a movie object.
 */

class Movie {
    public String title;
    private String year;
    private String type;
    String imdbID;

    Movie(String title, String year, String type, String imdbID){
        this.title = title;
        this.year = year;
        this.type = type;
        this.imdbID = imdbID;
    }

    public String toString() {
        return title + " (" + year + ")";
    }
}
