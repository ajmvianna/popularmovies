package edu.nanodegreeprojects.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import edu.nanodegreeprojects.popularmovies.R;
import edu.nanodegreeprojects.popularmovies.model.Movie;
import edu.nanodegreeprojects.popularmovies.utils.JsonParser;
import edu.nanodegreeprojects.popularmovies.utils.NetworkUtils;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private Context context;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        private final ImageView ivMovieThumbnail;

        MovieAdapterViewHolder(View view) {
            super(view);
            ivMovieThumbnail = view.findViewById(R.id.iv_movie_item);
            view.setOnClickListener(this);
            context = view.getContext();
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(movieList.get(adapterPosition));
        }
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String movieThumbnail = movieList.get(position).getThumbnailPath();
        Picasso.with(context).load(NetworkUtils.buildUrl(movieThumbnail).toString()).into(movieAdapterViewHolder.ivMovieThumbnail);
    }

    @Override
    public int getItemCount() {
        if (movieList.isEmpty()) return 0;
        return movieList.size();
    }

    public void setMovieData(String movieData) {
        List<Movie> movieList = new ArrayList<>();
        if (!movieData.equals("")) {
            try {
                movieList = JsonParser.convertHTTPReturnToMovie(movieData);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.movieList = movieList;
        } else {
            this.movieList.clear();
        }
        notifyDataSetChanged();
    }
}