package edu.nanodegreeprojects.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.net.URL;

import edu.nanodegreeprojects.popularmovies.adapters.MovieAdapter;
import edu.nanodegreeprojects.popularmovies.adapters.MovieAdapter.MovieAdapterOnClickHandler;
import edu.nanodegreeprojects.popularmovies.model.Movie;
import edu.nanodegreeprojects.popularmovies.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {

    private RecyclerView mainRecyclerView;
    private ProgressBar progressBar;
    private final int NUMBER_OF_COLUMNS = 3;
    private MovieAdapter movieAdapter;
    public static final String INTENT_EXTRA_OBJECT = "INTENT_EXTRA_OBJECT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadComponents();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mainRecyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(this);
        mainRecyclerView.setAdapter(movieAdapter);
        mainRecyclerView.setHasFixedSize(true);

        movieAdapter.setMovieData("");
        loadMovieData("popular", 1);

    }

    public void loadComponents() {
        mainRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        progressBar = findViewById(R.id.progress_bar);
    }


    @Override
    public void onClick(Movie movie) {

        Context context = this;
        Class movieDetailClass = MovieDetail.class;
        Intent intentToStartMovieDetailActivity = new Intent(context, movieDetailClass);
        intentToStartMovieDetailActivity.putExtra(INTENT_EXTRA_OBJECT, (Serializable) movie);
        startActivity(intentToStartMovieDetailActivity);
    }

    @SuppressLint("StaticFieldLeak")
    public class FetchVideos extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String queryType = params[0];
            String pageID = params[1];
            URL videosRequestUrl = NetworkUtils.buildUrl(queryType, pageID);

            try {
                String jsonVideosResponse = NetworkUtils
                        .getResponseFromHttpUrl(videosRequestUrl);

//                String[] simpleJsonWeatherData = JsonParser
//                        .getSimpleWeatherStringsFromJson(MainActivity.this, jsonWeatherResponse);

                System.out.println(jsonVideosResponse);
                return jsonVideosResponse;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String movieData) {
            progressBar.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                movieAdapter.setMovieData(movieData);
            } else {
                //showErrorMessage();
            }
        }

    }

    private void showErrorMessage() {

        //mRecyclerView.setVisibility(View.INVISIBLE);
//        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_LONG).show();
    }

    private void loadMovieData(String sortType, int pageId) {
        new FetchVideos().execute(sortType, String.valueOf(pageId));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_popular:
                movieAdapter.setMovieData("");
                loadMovieData("popular", 1);
                break;
            case R.id.sort_by_highest_rated:
                movieAdapter.setMovieData("");
                loadMovieData("top_rated", 1);
                break;
        }
        return true;
    }
}
