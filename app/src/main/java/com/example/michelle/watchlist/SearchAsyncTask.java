package com.example.michelle.watchlist;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Michelle on 14-11-2016.
 */

public class SearchAsyncTask extends AsyncTask<String, Integer, String>{
    Context context;
    SearchActivity activity;
    ArrayList<Movie> search_results;

    // Constructor
    public SearchAsyncTask(SearchActivity activity){
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
        this.search_results = new ArrayList<>();
    }

    //onPreExecute()
    protected void onPreExecute() {
        Toast.makeText(context, "Loading data", Toast.LENGTH_SHORT).show();
    }

    //doInBackground()
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer_search(params);
    }

    // onProgressUpdate()

    // onPostExecute()
    protected void onPostExecute(String result) {
        // result in de vorm van JSON
        super.onPostExecute(result);

        if (result.length() == 0) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        } else {
            //search_results = new ArrayList<>();

            try{
                JSONObject respObj = new JSONObject(result);
                JSONArray movie_results = respObj.getJSONArray("Search");

                for (int i = 0; i < movie_results.length(); i++) {
                    JSONObject movie = movie_results.getJSONObject(i);

                    String title = movie.getString("Title");
                    String year = movie.getString("Year");
                    String type = movie.getString("Type");
                    String imdbID = movie.getString("imdbID");
                    String poster = movie.getString("Poster");

                    search_results.add(new Movie(title, year, type, imdbID, poster, null));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}



