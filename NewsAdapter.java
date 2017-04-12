package com.example.bharadwaj.newsfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.R.id.list;

/**
 * Created by Bharadwaj on 4/1/17.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    private static final String LOG_TAG = NewsAdapter.class.getName();

    public NewsAdapter(Context context, List<News> news){
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.e(LOG_TAG, "In getView method");
        View listItemView = convertView;

        if(listItemView == null){
            Log.e(LOG_TAG, "listItemView is Null");
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item,parent,false);
        }

        Log.e(LOG_TAG, "Position's value :" + position);
        News currentNewsItem = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.news_title);
        title.setText(currentNewsItem.getTitle());

        TextView section = (TextView) listItemView.findViewById(R.id.section);
        section.setText(currentNewsItem.getSection());

        TextView type = (TextView) listItemView.findViewById(R.id.type);
        type.setText(currentNewsItem.getType());

        Log.e(LOG_TAG, "listViewItem set to :" + listItemView.toString());

        return listItemView;
    }
}
