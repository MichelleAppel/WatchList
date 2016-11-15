package com.example.michelle.watchlist;

/**
 * Created by Michelle on 15-11-2016.
 */

class Movie {
    private String title;
    private String year;
    private String type;
    private String imdbID;
    private String poster;

    Movie(String title, String year, String type, String imdbID, String poster){
        this.title = title;
        this.year = year;
        this.type = type;
        this.imdbID = imdbID;
        this.poster = poster;
    }

    public String toString() {
        return title + " (" + year + ")";
    }
}
