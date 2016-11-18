package com.example.michelle.watchlist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class InfoActivity extends AppCompatActivity {
    Boolean inWatchList = false;

    public SharedPreferences prefs;
    Intent intent;
    String title;
    String year;
    String imdbID;

    ImageButton eye_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Get imdbID from selected item
        intent = getIntent();
        imdbID = intent.getStringExtra("imdbID");

        // Get preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Determine if movie is on Watch List and set icon appearance
        eye_button = (ImageButton) findViewById(R.id.eye_button);
        Map<String, ?> allIDs = prefs.getAll();
        for (Map.Entry<String, ?> id : allIDs.entrySet()) {
            if(id.getKey().equals(imdbID)) {
                inWatchList = true;
                eye_button.setImageResource(R.drawable.open);
                break;
            } else {
                inWatchList = false;
                eye_button.setImageResource(R.drawable.closed);
            }
        }

        // Retrieve info with infoAsyncTask
        final InfoAsyncTask infoAsyncTask = new InfoAsyncTask(this);
        infoAsyncTask.execute(imdbID);
        String result;

        try {
            result = infoAsyncTask.get();
            try{
                JSONObject movie_info = new JSONObject(result);

                title = movie_info.getString("Title");
                year = movie_info.getString("Year");
                String type = movie_info.getString("Type");
                String poster = movie_info.getString("Poster");
                String plot = movie_info.getString("Plot");
                String runtime = movie_info.getString("Runtime");
                String genre = movie_info.getString("Genre");
                String writer = movie_info.getString("Writer");
                String actors = movie_info.getString("Actors");
                String awards = movie_info.getString("Awards");
                String language = movie_info.getString("Language");
                String rating = movie_info.getString("imdbRating");
                String releaseDate = movie_info.getString("Released");

                TextView titleView = (TextView)findViewById(R.id.titleView);
                titleView.setText(title);

                ImageView posterView = (ImageView) findViewById(R.id.posterView);
                if (poster.equals("N/A")) {
                    posterView.setVisibility(View.GONE);
                } else {
                    new DownloadImageTask(posterView).execute(poster);
                }

                TextView yearView = (TextView)findViewById(R.id.yearView);
                yearView.setText(year);

                TextView typeView = (TextView)findViewById(R.id.typeView);
                typeView.setText(type.substring(0, 1).toUpperCase() + type.substring(1));

                TextView writerView = (TextView)findViewById(R.id.writerView);
                if (writer.equals("N/A")) {
                    findViewById(R.id.Writer).setVisibility(View.GONE);
                    writerView.setVisibility(View.GONE);
                } else {
                    writerView.setText(actors);
                }

                TextView genreView = (TextView)findViewById(R.id.genreView);
                if (genre.equals("N/A")) {
                    findViewById(R.id.Genre).setVisibility(View.GONE);
                    genreView.setVisibility(View.GONE);
                } else {
                    genreView.setText(genre);
                }


                TextView runTimeView = (TextView)findViewById(R.id.runTimeView);
                if (runtime.equals("N/A")) {
                    findViewById(R.id.Runtime).setVisibility(View.GONE);
                    runTimeView.setVisibility(View.GONE);
                } else {
                    runTimeView.setText(runtime);
                }

                TextView ratingView = (TextView)findViewById(R.id.ratingView);
                if (rating.equals("N/A")) {
                    findViewById(R.id.Rating).setVisibility(View.GONE);
                    ratingView.setVisibility(View.GONE);
                } else {
                    ratingView.setText(rating);
                }

                TextView releaseView = (TextView)findViewById(R.id.releaseView);
                if (releaseDate.equals("N/A")) {
                    findViewById(R.id.Release).setVisibility(View.GONE);
                    releaseView.setVisibility(View.GONE);
                } else {
                    releaseView.setText(releaseDate);
                }

                TextView actorView = (TextView)findViewById(R.id.actorView);
                if (actors.equals("N/A")) {
                    findViewById(R.id.Actor).setVisibility(View.GONE);
                    actorView.setVisibility(View.GONE);
                } else {
                    actorView.setText(actors);
                }

                TextView awardView = (TextView)findViewById(R.id.awardView);
                if (awards.equals("N/A")) {
                    findViewById(R.id.Award).setVisibility(View.GONE);
                    awardView.setVisibility(View.GONE);
                } else {
                    awardView.setText(awards);
                }

                TextView languageView = (TextView)findViewById(R.id.languageView);
                if (language.equals("N/A")) {
                    findViewById(R.id.Language).setVisibility(View.GONE);
                    languageView.setVisibility(View.GONE);
                } else {
                    languageView.setText(language);
                }

                TextView plotView = (TextView)findViewById(R.id.plotView);
                if (plot.equals("N/A")) {
                    plotView.setVisibility(View.GONE);
                } else {
                    plotView.setText(plot);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                showToast();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            showToast();
        } catch (ExecutionException e) {
            e.printStackTrace();
            showToast();
        }
    }

    public void showToast() {
        Toast.makeText(this, "Oops, something went wrong", Toast.LENGTH_SHORT).show();
    }

    public void watchList_addremove(View view) {
        // Shared Preferences
        SharedPreferences.Editor editor = prefs.edit();

        if(!inWatchList) {
            editor.putString(imdbID, title + " ("+year+")");
            eye_button.setImageResource(R.drawable.open);
            inWatchList = true;
        } else {
            editor.remove(imdbID);
            eye_button.setImageResource(R.drawable.closed);
            inWatchList = false;
        }
        editor.apply();
    }
}
