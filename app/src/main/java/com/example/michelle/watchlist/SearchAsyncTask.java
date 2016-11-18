package com.example.michelle.watchlist;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Michelle on 14-11-2016.
 */

public class SearchAsyncTask extends AsyncTask<String, Integer, String>{
    Context context;
    SearchActivity activity;
    ArrayList<Movie> search_results;
    ListView listView;

    // Constructor
    public SearchAsyncTask(SearchActivity activity, ListView listView){
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
        this.search_results = new ArrayList<>();
        this.listView = listView;
    }

    //onPreExecute()
    protected void onPreExecute() {
    }

    //doInBackground()
    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer_search(params[0].replaceAll("\\s+","+"));
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
                JSONObject respObj = new JSONObject(result);
                JSONArray movie_results = respObj.getJSONArray("Search");
                if (movie_results.length() > 0) {

                    for (int i = 0; i < movie_results.length(); i++) {
                        JSONObject movie = movie_results.getJSONObject(i);

                        String title = movie.getString("Title");
                        String year = movie.getString("Year");
                        String type = movie.getString("Type");
                        String imdbID = movie.getString("imdbID");
                        String poster = movie.getString("Poster");

                        search_results.add(new Movie(title, year, type, imdbID, poster, null));
                    }
                    ArrayAdapter moviesAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, search_results);
                    listView.setAdapter(moviesAdapter);
                } else {
                    Toast.makeText(context, "No results are found", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}



