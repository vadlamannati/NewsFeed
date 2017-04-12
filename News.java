package com.example.bharadwaj.newsfeed;

import android.util.Log;

/**
 * Created by Bharadwaj on 4/1/17.
 */

public class News {

    private static final String LOG_TAG = News.class.getName();
    private static int mNewsObjectCounter = 0;

    private String mType;
    private String mSection;
    private String mTitle;
    private String mUrl;


    public News(String type, String section, String title, String url){

        this.mType = type;
        this.mSection = section;
        this.mTitle = title;
        this.mUrl = url;

        mNewsObjectCounter++;
        Log.e(LOG_TAG,"Creating News object : " + mNewsObjectCounter);
    }


    public String getType() {
        return mType;
    }

    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
