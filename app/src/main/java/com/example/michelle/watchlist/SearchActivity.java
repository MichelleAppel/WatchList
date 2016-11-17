package com.example.michelle.watchlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;


public class SearchActivity extends AppCompatActivity {

    private EditText search_bar;
    private ListView search_result_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_bar = (EditText) findViewById(R.id.search_bar);
        search_result_listView = (ListView)findViewById(R.id.movie_listView);
    }

    // Performs action when clicked on search button
    public void search(View view) {
        // Get text from search bar
        String search_term = search_bar.getText().toString();

        // Search items
        final SearchAsyncTask searchAsyncTask = new SearchAsyncTask(this);
        searchAsyncTask.execute(search_term);

        // Add results to ListView
        final ArrayAdapter<Movie> moviesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, searchAsyncTask.search_results);



        search_result_listView.setAdapter(moviesAdapter);

        final Intent intent = new Intent(this, InfoActivity.class);
        // Perform action when clicked on item
        search_result_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String moviePicked = "You selected " + String.valueOf(searchAsyncTask.search_results.get(position));
                Toast.makeText(SearchActivity.this, moviePicked, Toast.LENGTH_SHORT).show();

                intent.putExtra("title", searchAsyncTask.search_results.get(position).title);
                intent.putExtra("year", searchAsyncTask.search_results.get(position).year);
                intent.putExtra("type", searchAsyncTask.search_results.get(position).type);
                intent.putExtra("imdbID", searchAsyncTask.search_results.get(position).imdbID);
                intent.putExtra("poster", searchAsyncTask.search_results.get(position).poster);

                startActivity(intent);
            }
        });
    }
}
