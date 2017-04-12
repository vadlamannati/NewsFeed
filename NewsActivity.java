package com.example.bharadwaj.newsfeed;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = NewsActivity.class.getName();
    private static final String NEWS_URL = "https://content.guardianapis.com/search?q=debate%20AND%20(economy%20OR%20immigration%20education)&tag=politics/politics&from-date=2014-01-01&api-key=test";
    //Network Key = a3835453-8404-40b6-be51-be0204c405ae

    private NewsAdapter mNewsAdapter;
    private TextView mEmptyNewsView;
    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.list_item);

        mEmptyNewsView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyNewsView);

        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(mNewsAdapter);


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        Log.e(LOG_TAG, "Connectivity verified : " + connMgr.toString());

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(null !=networkInfo && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            Log.e(LOG_TAG, "Initializing Loader");
        }else{
            mEmptyNewsView.setText("No internet connection");
        }




    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Log.e(LOG_TAG, "In onCreateLoader method");

        return new NewsLoader(this, NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        Log.e(LOG_TAG, "In onLoadFinished method");

        mEmptyNewsView.setText("No News Items found");

        mNewsAdapter.clear();

        if(null !=news && !news.isEmpty()){
            mNewsAdapter.addAll(news);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.e(LOG_TAG, "In onLoaderReset method");

    }
}
