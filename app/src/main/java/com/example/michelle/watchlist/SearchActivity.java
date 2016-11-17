package com.example.michelle.watchlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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

        Intent intent = getIntent();
        if(intent != null) {
            String search_term = intent.getStringExtra("search_term");
            System.out.println(search_term);
            put_results_in_listView(search_term);
        }

        search_bar = (EditText)findViewById(R.id.search_bar);

        search_bar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                //If the keyevent is a key-down event on the "enter" button
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search(search_bar);
                    return true;
                }
                return false;
            }
        });
    }

    // Performs action when clicked on search button
    public void search(View view) {
        // Get text from search bar
        String search_term = search_bar.getText().toString();
        put_results_in_listView(search_term);
        // Search items
    }

    public void put_results_in_listView(String search_term) {
        final SearchAsyncTask searchAsyncTask = new SearchAsyncTask(SearchActivity.this, search_result_listView);
        searchAsyncTask.execute(search_term);

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
                finish();
            }
        });
    }
}
