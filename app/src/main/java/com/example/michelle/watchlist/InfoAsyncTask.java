package com.example.michelle.watchlist;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Michelle on 17-11-2016.
 */

public class InfoAsyncTask extends AsyncTask<String, Integer, String> {
    Context context;
    InfoActivity activity;
    public Movie movie;

    // Constructor
    public InfoAsyncTask(InfoActivity activity){
        super();
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
    }

    //onPreExecute()
    protected void onPreExecute() {
        //Toast.makeText(context, "Loading data", Toast.LENGTH_SHORT).show();
    }

    //doInBackground()
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer_info(params);
    }

    // onProgressUpdate()

    // onPostExecute()
    protected void onPostExecute(String result) {
        // result in de vorm van JSON
        super.onPostExecute(result);

        if (result.length() == 0) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {

            try{
                JSONObject movie_info = new JSONObject(result);

                String title = movie_info.getString("Title");
                String year = movie_info.getString("Year");
                String type = movie_info.getString("Type");
                String imdbID = movie_info.getString("imdbID");
                String poster = movie_info.getString("Poster");
                String plot = movie_info.getString("Plot");

                movie = new Movie(title, year, type, imdbID, poster, plot);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}