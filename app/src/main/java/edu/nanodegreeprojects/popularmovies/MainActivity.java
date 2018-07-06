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
import android.widget.TextView;

import java.net.URL;

import edu.nanodegreeprojects.popularmovies.adapters.MovieAdapter;
import edu.nanodegreeprojects.popularmovies.adapters.MovieAdapter.MovieAdapterOnClickHandler;
import edu.nanodegreeprojects.popularmovies.model.Movie;
import edu.nanodegreeprojects.popularmovies.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {

    private RecyclerView mainRecyclerView;
    private ProgressBar progressBar;
    private TextView tvMessageError;
    private final int NUMBER_OF_COLUMNS = 2;
    private final String RECYCLER_VIEW_STATE_TAG = "RECYCLER_VIEW_STATE";
    private MovieAdapter movieAdapter;
    public static final String INTENT_EXTRA_OBJECT = "INTENT_EXTRA_OBJECT";
    private Parcelable listPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadComponents();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mainRecyclerView.setLayoutManager(gridLayoutManager);
        movieAdapter = new MovieAdapter(this);
        mainRecyclerView.setAdapter(movieAdapter);
        mainRecyclerView.setHasFixedSize(false);

        loadMovieData("popular", 1);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        if (mainRecyclerView.getLayoutManager().onSaveInstanceState() != null)
            outState.putParcelable(RECYCLER_VIEW_STATE_TAG, mainRecyclerView.getLayoutManager().onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null)
            listPosition = savedInstanceState.getParcelable(RECYCLER_VIEW_STATE_TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (listPosition != null)
            mainRecyclerView.getLayoutManager().onRestoreInstanceState(listPosition);
    }

    private void loadComponents() {
        mainRecyclerView = findViewById(R.id.main_recycler_view);
        progressBar = findViewById(R.id.progress_bar);
        tvMessageError = findViewById(R.id.tv_message_error);
    }

    private void loadMovies(String movieData) {
        if (movieData != null) {
            movieAdapter.setMovieData(movieData);
        }
    }

    @Override
    public void onClick(Movie movie) {

        Context context = this;
        Class movieDetailClass = MovieDetail.class;
        Intent intentToStartMovieDetailActivity = new Intent(context, movieDetailClass);
        intentToStartMovieDetailActivity.putExtra(INTENT_EXTRA_OBJECT, movie);
        startActivity(intentToStartMovieDetailActivity);
    }

    @SuppressLint("StaticFieldLeak")
    class FetchVideos extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            tvMessageError.setVisibility(View.INVISIBLE);
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
                return NetworkUtils.getResponseFromHttpUrl(videosRequestUrl);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String movieData) {
            progressBar.setVisibility(View.INVISIBLE);
            tvMessageError.setVisibility(View.INVISIBLE);
            if (movieData != null)
                loadMovies(movieData);
            else
                showErrorMessage();

        }
    }

    private void showErrorMessage() {

        progressBar.setVisibility(View.INVISIBLE);
        tvMessageError.setVisibility(View.VISIBLE);
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
