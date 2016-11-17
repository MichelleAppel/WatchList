package com.example.michelle.watchlist;

/**
 * Created by Michelle on 15-11-2016.
 */

class Movie {
    public String title;
    public String year;
    public String type;
    public String imdbID;
    public String poster;
    public String plot;

    Movie(String title, String year, String type, String imdbID, String poster, String plot){
        this.title = title;
        this.year = year;
        this.type = type;
        this.imdbID = imdbID;
        this.poster = poster;
        this.plot = plot;
    }

    public String toString() {
        return title + " (" + year + ")";
    }
}
