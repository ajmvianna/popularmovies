package edu.nanodegreeprojects.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    // private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIES_DB_URL = "https://api.themoviedb.org/3/movie";
    private static final String THUMBNAILS_DB_URL = "http://image.tmdb.org/t/p";

    private static final String MY_API_KEY = "74668b7907bb160121c3e7a9f2732341";
    private static final String API_KEY_TAG = "api_key";

    //language support can be added on future releases
    private static final String MY_LANGUAGE_PREFERENCE = "en-us";
    private static final String LANGUAGE_PREFERENCE_TAG = "language";
    private static final String PAGE_ID_TAG = "page";
    private static final String LOG_TAG = "LOG_APP_URL_BUILT";

    private static final String THUMBNAILS_SIZE = "w185";



    /**
     * This method returns the URL built by the arguments passed.
     *
     * @param queryType The type of the query (popular | top_rated).
     * @param pageID    The number of the page that you want to fetch (1-996).
     * @return The URL built by the arguments passed.
     */
    public static URL buildUrl(String queryType, String pageID) {
        Uri builtUri = Uri.parse(MOVIES_DB_URL).buildUpon()
                .appendPath(queryType)
                .appendQueryParameter(API_KEY_TAG, MY_API_KEY)
                .appendQueryParameter(LANGUAGE_PREFERENCE_TAG, MY_LANGUAGE_PREFERENCE)
                .appendQueryParameter(PAGE_ID_TAG, pageID)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "Built URI " + url);

        return url;
    }

    public static URL buildUrl(String thumbnailPath) {
        Uri builtUri = Uri.parse(THUMBNAILS_DB_URL).buildUpon()
                .appendPath(THUMBNAILS_SIZE)
                .appendEncodedPath(thumbnailPath)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(LOG_TAG, "Built URI " + url);

        return url;
    }
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}