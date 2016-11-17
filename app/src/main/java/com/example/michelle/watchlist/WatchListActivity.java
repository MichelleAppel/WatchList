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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

public class WatchListActivity extends AppCompatActivity {

    EditText search_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_list);

        // The ArrayList that contains all saved movie IDs
        final ArrayList<String> allSavedIDs = new ArrayList<>();
        ArrayList<String> allSavedTitles = new ArrayList<>();

        // Shared Preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

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

        search_bar = (EditText)findViewById(R.id.search_bar);

        search_bar.setOnKeyListener(new OnKeyListener() {
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

    public void search(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("search_term", search_bar.getText().toString());
        startActivity(intent);
    }
}
