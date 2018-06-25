package edu.nanodegreeprojects.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.nanodegreeprojects.popularmovies.model.Movie;
import edu.nanodegreeprojects.popularmovies.utils.NetworkUtils;

public class MovieDetail extends AppCompatActivity {

    private ImageView ivMovieThumbnail;
    private TextView tvMovieTitle;
    private TextView tvMovieReleaseDate;
    private TextView tvMovieVoteAverage;
    private TextView tvMovieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        loadComponents();

        if (getIntent().hasExtra(MainActivity.INTENT_EXTRA_OBJECT)) {
            Movie movie = (Movie) getIntent().getSerializableExtra(MainActivity.INTENT_EXTRA_OBJECT);
            fillMovieFields(movie.getTitle(), movie.getReleaseDate(), movie.getVoteAverage(), movie.getOverview(), movie.getThumbnailPath());
        }
    }

    public void loadComponents() {
        ivMovieThumbnail = findViewById(R.id.iv_movie_item_detail);
        tvMovieTitle = findViewById(R.id.tv_movie_title);
        tvMovieReleaseDate = findViewById(R.id.tv_movie_release_date);
        tvMovieVoteAverage = findViewById(R.id.tv_movie_vote_average);
        tvMovieOverview = findViewById(R.id.tv_movie_overview);
    }

    public void fillMovieFields(String movieTitle, String movieReleaseDate, String movieVoteAverage, String movieOverview, String movieThumbnail) {

        tvMovieTitle.setText(movieTitle);
        tvMovieReleaseDate.setText(movieReleaseDate);
        tvMovieVoteAverage.setText(movieVoteAverage);
        tvMovieOverview.setText(movieOverview);
        Picasso.with(this).load(NetworkUtils.buildUrl(movieThumbnail).toString()).into(ivMovieThumbnail);
    }
}
