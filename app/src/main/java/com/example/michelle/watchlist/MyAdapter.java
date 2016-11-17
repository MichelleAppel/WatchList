package com.example.michelle.watchlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Michelle on 15-11-2016.
 */

class MyAdapter extends ArrayAdapter<String>{

    public MyAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());

        View view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        String movie = getItem(position);

        TextView textView = (TextView) view.findViewById(R.id.textView1);

        textView.setText(movie);

        return view;
    }
}
