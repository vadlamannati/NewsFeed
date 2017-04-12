package com.example.bharadwaj.newsfeed;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bharadwaj on 4/1/17.
 */

public class QueryUtils {


    private static final String LOG_TAG = QueryUtils.class.getName();

    public static List<News> fetchNewsData(String urlString) {
        Log.e(LOG_TAG, "fetchNewsData method");
        URL url = createUrl(urlString);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
            Log.e(LOG_TAG, "Length of JSON Response returned is : " + jsonResponse.toString().length());
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error while making HTTP Request", e);
        }

        List<News> news = extractFeatureFromJson(jsonResponse);

        return news;
    }

    public static URL createUrl(String urlString) {
        Log.e(LOG_TAG, "createUrl method");
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malformed URL exception. Please check the URL string");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        Log.e(LOG_TAG, "makeHttpRequest method");
        String jsonResponse = "";

        if (null == url) {
            Log.e(LOG_TAG, "URL is empty");
            return jsonResponse;
        } else {
            Log.e(LOG_TAG, "URL string provided is : " + url);
        }

        HttpURLConnection httpUrlConnection = null;
        InputStream inputStream = null;

        try {
            httpUrlConnection = (HttpURLConnection) url.openConnection();
            Log.e(LOG_TAG, "httpUrlConnection Response : " + httpUrlConnection.getResponseCode());
            Log.e(LOG_TAG, "httpUrlConnection Message : " + httpUrlConnection.getResponseMessage());

            httpUrlConnection.setConnectTimeout(10000);
            httpUrlConnection.setReadTimeout(15000);
            httpUrlConnection.setRequestMethod("GET");
            Log.e(LOG_TAG, "httpUrlConnection request method set to : " + httpUrlConnection.getRequestMethod());

            httpUrlConnection.connect();
            //Log.e(LOG_TAG, "httpUrlConnection request properties are : " + httpUrlConnection.getRequestProperties());

            if (httpUrlConnection.getResponseCode() == 200) {
                inputStream = httpUrlConnection.getInputStream();
                Log.e(LOG_TAG, "Input Stream to string is : " + inputStream.toString());
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error Response Code: " + httpUrlConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "IO Error while getting News results", e);
        } finally {

            if (null != httpUrlConnection) {
                httpUrlConnection.disconnect();
                Log.e(LOG_TAG, "httpUrlConnection disconnected");
            }

            if (null != inputStream) {
                inputStream.close();
                Log.e(LOG_TAG, "Input Stream closed");
            }

        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.e(LOG_TAG, "readFromStream method");
        StringBuilder output = new StringBuilder();

        if (null != inputStream) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            //Log.e(LOG_TAG, "Current line to add : " + line);
            while (null != line) {
                output.append(line);
                line = bufferedReader.readLine();
            }
            Log.e(LOG_TAG, "Length of String Builder is : " + output.toString().length());
        } else {
            Log.e(LOG_TAG, "Input Stream is empty");
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJSON) {
        Log.e(LOG_TAG, "extractFeatureFromJson method");
        if (TextUtils.isEmpty(newsJSON)) {
            Log.e(LOG_TAG, "news JSON is empty");
            return null;
        }else{
            Log.e(LOG_TAG, "Length of News JSON is : " + newsJSON.length());
        }

        List<News> news = new ArrayList<>();

        try {
            JSONObject baseJSONResponse = new JSONObject(newsJSON);

            JSONObject newsResponse = baseJSONResponse.getJSONObject("response");
            Log.e(LOG_TAG, "News Response String Length is : " + newsResponse.toString().length());

            JSONArray newsArray = newsResponse.getJSONArray("results");
            Log.e(LOG_TAG, "Length of JSON array is : " + newsArray.length());

            for (int i = 0; i < newsArray.length(); i++) {
                Log.e(LOG_TAG, "Building news items : i : " + i);

                JSONObject currentNews = newsArray.getJSONObject(i);

                Log.e(LOG_TAG, "Current JSON : " + currentNews.toString());

                String type = currentNews.getString("type");
                String section = currentNews.getString("sectionId");
                String title = currentNews.getString("webTitle");
                String url = currentNews.getString("webUrl");

                news.add(new News(type, section, title, url));
            }
            Log.e(LOG_TAG, "News items are : " + news);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSON Exception while parsing JSON response : " + e);
        }
        return news;
    }


}