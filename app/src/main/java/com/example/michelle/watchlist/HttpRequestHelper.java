
package com.example.michelle.watchlist;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Michelle on 15-11-2016.
 * HttpRequestHelper
 */

public class HttpRequestHelper {

    static String downloadFromServer_search(String... params) {
        String url1 = "http://www.omdbapi.com/?s=";
        String url2 = "&y=&plot=short&r=json";

        URL url = null;
        String result = "";
        String input = params[0];
        String completeUrl = url1 + input + url2;

        try{
            url = new URL(completeUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connection;
        if (url != null) {
            try {
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");

                // Get response code
                Integer responseCode = connection.getResponseCode();
                // If response successful
                if(200 <= responseCode && responseCode <= 299) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String line;
                    while((line = br.readLine()) != null) {
                        result = result + line;
                    }
                    return result;
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                    String line;
                    while((line = br.readLine()) != null) {
                        result = result + line;
                    }
                    return result;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "no internet";
    }

    static String downloadFromServer_info(String... params) {
        String url1 = "http://www.omdbapi.com/?i=";
        String url2 = "&plot=short&r=json";

        URL url = null;
        String result = "";
        String input = params[0];
        String completeUrl = url1 + input + url2;

        try{
            url = new URL(completeUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connection;
        if (url != null) {
            try {
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");

                // get response code
                Integer responseCode = connection.getResponseCode();

                // if 200-300, read inputstream
                if(200 <= responseCode && responseCode <= 299) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String line;
                    while((line = br.readLine()) != null) {
                        result = result + line;
                    }
                    return result;
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                    String line;
                    while((line = br.readLine()) != null) {
                        result = result + line;
                    }
                    return result;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "no internet";
    }
}
