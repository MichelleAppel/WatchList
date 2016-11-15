package com.example.michelle.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void search(View view) {
        // Get text from search bar
        String search_term = ((EditText)findViewById(R.id.search_bar)).getText().toString();

        // Search movies
        MovieAsyncTask movieAsyncTask = new MovieAsyncTask(this);
        movieAsyncTask.execute(search_term);

        // ListView of the search results
        ListView search_result_listView = (ListView)findViewById(R.id.movie_listView);

        // Add results to ListView
        ArrayAdapter<Movie> moviesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, movieAsyncTask.search_results);
        search_result_listView.setAdapter(moviesAdapter);

    }
}
