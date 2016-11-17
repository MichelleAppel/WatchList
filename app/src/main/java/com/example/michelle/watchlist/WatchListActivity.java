package com.example.michelle.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class WatchListActivity extends AppCompatActivity {

    public SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);


        // The ArrayList that contains all saved movie IDs
        final ArrayList<String> allSavedIDs = new ArrayList<>();
        final ArrayList<String> allSavedTitles = new ArrayList<>();

        // Shared Preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Map<String, ?> allIDs = prefs.getAll();

        for (Map.Entry<String, ?> entry : allIDs.entrySet()) {
            allSavedIDs.add(entry.getValue().toString());
            allSavedTitles.add(entry.getKey());
        }

        final ListView WatchList_ListView = (ListView)findViewById(R.id.WatchList_ListView);


        final ArrayAdapter<String> moviesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, allSavedTitles);


        WatchList_ListView.setAdapter(moviesAdapter);

        final Intent intent = new Intent(this, InfoActivity.class);
        // Perform action when clicked on item
        WatchList_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("imdbID", String.valueOf(allSavedIDs.get(position)));
                startActivity(intent);
            }
        });
    }

    public void to_search(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
