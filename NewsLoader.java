package com.example.bharadwaj.newsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import java.util.List;

/**
 * Created by Bharadwaj on 4/8/17.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private static final String LOG_TAG = NewsLoader.class.getName();
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);

        mUrl=url;
    }


    @Override
    protected void onStartLoading() {
        Log.e(LOG_TAG, "onStartLoading Method");
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        Log.e(LOG_TAG, "Loading in Background");

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
