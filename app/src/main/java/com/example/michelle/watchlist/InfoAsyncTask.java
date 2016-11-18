package com.example.michelle.watchlist;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Michelle on 17-11-2016.
 * Gets movie info from OMDB.
 */

class InfoAsyncTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private InfoActivity activity;

    // Constructor
    public InfoAsyncTask(InfoActivity activity){
        super();
        this.activity = activity;
        this.context = this.activity.getApplicationContext();
    }

    protected String doInBackground(String... params) {
        return HttpRequestHelper.downloadFromServer_info(params);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result.equals("no internet")) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        } else {
            super.onPostExecute(result);
        }
    }
}