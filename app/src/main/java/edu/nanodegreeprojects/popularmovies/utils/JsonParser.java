package edu.nanodegreeprojects.popularmovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.nanodegreeprojects.popularmovies.model.Movie;


public final class JsonParser {

    private static final String MAIN_JSON_NODE = "results";

    public static List<Movie> convertHTTPReturnToMovie(String httpReturn) throws JSONException {
        List<Movie> movieList = new ArrayList<>();
        Movie movie;

        JSONObject jsonObjectsList = new JSONObject(httpReturn);
        JSONArray jsonArray = jsonObjectsList.getJSONArray(MAIN_JSON_NODE);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            movie = new Movie(jsonObject.getString("id"),
                    jsonObject.getString("title"),
                    jsonObject.getString("original_title"),
                    jsonObject.getString("poster_path"),
                    jsonObject.getString("overview"),
                    jsonObject.getString("vote_average"),
                    jsonObject.getString("release_date"),
                    jsonObject.getString("vote_count"),
                    jsonObject.getString("original_language")
                    );

            movieList.add(movie);
        }

        return movieList;
    }
}