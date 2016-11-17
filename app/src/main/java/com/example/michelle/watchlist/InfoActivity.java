package com.example.michelle.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class InfoActivity extends AppCompatActivity {

    public SharedPreferences prefs;
    Intent intent;
    String title;
    String imdbID;

    Button watchList_addremove;
    ImageView posterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        intent = getIntent();
        imdbID = intent.getStringExtra("imdbID");

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        watchList_addremove = (Button) findViewById(R.id.watchList_addremove);

        Map<String, ?> allIDs = prefs.getAll();
        for (Map.Entry<String, ?> id : allIDs.entrySet()) {
            if(id.getValue().equals(imdbID)) {
                watchList_addremove.setText("Remove from Watch List");
                break;
            } else {
                watchList_addremove.setText("Add to Watch List");
            }
        }

        final InfoAsyncTask infoAsyncTask = new InfoAsyncTask(this);
        infoAsyncTask.execute(imdbID);

        String result;
        Movie movie;

        try {
            result = infoAsyncTask.get();

            try{
                JSONObject movie_info = new JSONObject(result);

                title = movie_info.getString("Title");
                String year = movie_info.getString("Year");
                String type = movie_info.getString("Type");
                String poster = movie_info.getString("Poster");
                String plot = movie_info.getString("Plot");

                movie = new Movie(title, year, type, imdbID, poster, plot);

                TextView titleView = (TextView)findViewById(R.id.titleView);
                titleView.setText(movie.title);

                TextView yearView = (TextView)findViewById(R.id.yearView);
                yearView.setText(movie.year);

                TextView typeView = (TextView)findViewById(R.id.typeView);
                typeView.setText(movie.type);

                TextView plotView = (TextView)findViewById(R.id.plotView);
                plotView.setText(movie.plot);

                posterView = (ImageView) findViewById(R.id.posterView);

                if (poster.equals("N/A")) {
                    posterView.setVisibility(View.GONE);
                } else {
                    new DownloadImageTask(posterView).execute(poster);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void watchList_addremove(View view) {
        // Shared Preferences
        SharedPreferences.Editor editor = prefs.edit();

        System.out.println(watchList_addremove.getText());

        if(watchList_addremove.getText().equals("Add to Watch List")) {
            editor.putString(title, imdbID);
        } else {
            editor.remove(title);
        }
        editor.commit();
        System.out.println(prefs.getAll());

        //Intent intent = new Intent(this, WatchListActivity.class);
        //startActivity(intent);
    }
}
