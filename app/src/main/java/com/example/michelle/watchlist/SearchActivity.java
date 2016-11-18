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

        // Get search term from WatchList activity
        Intent intent = getIntent();
        if(intent != null) {
            String search_term = intent.getStringExtra("search_term");
            put_results_in_listView(search_term);
        }

        search_bar = (EditText)findViewById(R.id.search_bar);
        // Performs action when enter is pressed
        search_bar.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    search(search_bar);
                    return true;
                }
                return false;
            }
        });
    }

    // Performed when clicked on enter
    public void search(View view) {
        // Get text from search bar
        String search_term = search_bar.getText().toString();
        put_results_in_listView(search_term);
    }

    public void put_results_in_listView(String search_term) {
        // Calls results from AsyncTask
        final SearchAsyncTask searchAsyncTask = new SearchAsyncTask(SearchActivity.this, search_result_listView);
        searchAsyncTask.execute(search_term);

        // Perfoms action when clicked on item
        final Intent intent = new Intent(this, InfoActivity.class);
        search_result_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("imdbID", searchAsyncTask.search_results.get(position).imdbID);
                startActivity(intent);
                finish();
            }
        });
    }
}
