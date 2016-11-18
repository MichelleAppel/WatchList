package com.example.michelle.watchlist;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/*
 * Created by Michelle on 14-11-2016.
 * Gets search results from Open Movie Database.
 */

class SearchAsyncTask extends AsyncTask<String, Integer, String>{
    private SearchActivity activity;
    ArrayList<Movie> search_results;
    private ListView listView;

    // Constructor
    SearchAsyncTask(SearchActivity activity, ListView listView){
        this.activity = activity;
        this.search_results = new ArrayList<>();
        this.listView = listView;
    }

    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer_search(params[0].replaceAll("\\s+","+"));
    }

    protected void onPostExecute(String result) {
        if (result.equals("no internet")) {
            Toast.makeText(this.activity.getApplicationContext(), "No internet connection",
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onPostExecute(result);

            try {
                JSONObject respObj = new JSONObject(result);
                JSONArray movie_results = respObj.getJSONArray("Search");

                // Get search results and put in ArrayList
                for (int i = 0; i < movie_results.length(); i++) {
                    JSONObject movie = movie_results.getJSONObject(i);

                    String title = movie.getString("Title");
                    String year = movie.getString("Year");
                    String type = movie.getString("Type");
                    String imdbID = movie.getString("imdbID");

                    search_results.add(new Movie(title, year, type, imdbID));
                }

                // Set results to adapter
                ArrayAdapter moviesAdapter = new ArrayAdapter<>(activity,
                        android.R.layout.simple_list_item_1, search_results);
                listView.setAdapter(moviesAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this.activity.getApplicationContext(), "No results are found",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}



