package com.example.michelle.watchlist;

import android.view.View.OnKeyListener;
import android.view.View;
import android.view.KeyEvent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WatchListActivity extends AppCompatActivity {

    ArrayAdapter<String> moviesAdapter;
    EditText search_bar;
    SharedPreferences prefs;

    // The ArrayLists that contains all saved movie IDs and titles
    ArrayList<String> allSavedIDs = new ArrayList<>();
    ArrayList<String> allSavedTitles = new ArrayList<>();

    ListView watchList_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);

        onResume();

        // Make intent
        final Intent intent = new Intent(this, InfoActivity.class);
        // Perform action when clicked on item
        watchList_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("imdbID", String.valueOf(allSavedIDs.get(position)));
                startActivity(intent);
            }
        });

        // Search bar
        search_bar = (EditText)findViewById(R.id.search_bar);
        // When enter is pressed in the search bar, call search method
        search_bar.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        search(search_bar);
                    return true;
                }
                return false;
            }
        });
    }

    // Go to search activity with search tag
    public void search(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("search_term", search_bar.getText().toString());
        startActivity(intent);
    }

    // Performed when clicked on delete icon
    public void delete(View view) {
        Toast.makeText(this, "All cleared", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();

        allSavedIDs.clear();
        allSavedTitles.clear();
        moviesAdapter.notifyDataSetChanged();
    }

    // Performed when pressed on back button
    @Override
    protected void onResume() {
        super.onResume();

        // Search bar
        ((EditText)findViewById(R.id.search_bar)).setText("");

        allSavedIDs = new ArrayList<>();
        allSavedTitles = new ArrayList<>();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Gets the shared preferences and puts it in arraylists
        Map<String, ?> allIDs = prefs.getAll();
        for (Map.Entry<String, ?> entry : allIDs.entrySet()) {
            allSavedIDs.add(entry.getKey());
            allSavedTitles.add(entry.getValue().toString());
        }

        // Make new adapter and set to ListView
        moviesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allSavedTitles);
        watchList_listView = (ListView)findViewById(R.id.WatchList_ListView);
        watchList_listView.setAdapter(moviesAdapter);
    }
}
