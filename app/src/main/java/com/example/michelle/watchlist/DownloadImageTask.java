package com.example.michelle.watchlist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Michelle on 17-11-2016.
 * Gets image from OMDB and shows in ImageView.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView poster;

    public DownloadImageTask(ImageView poster) {
        this.poster = poster;
    }

    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap image = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            image = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result) {
        poster.setImageBitmap(result);
    }
}