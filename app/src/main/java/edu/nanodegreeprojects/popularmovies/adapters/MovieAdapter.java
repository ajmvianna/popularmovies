/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.nanodegreeprojects.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import edu.nanodegreeprojects.popularmovies.model.Movie;
import edu.nanodegreeprojects.popularmovies.R;
import edu.nanodegreeprojects.popularmovies.utils.JsonParser;
import edu.nanodegreeprojects.popularmovies.utils.NetworkUtils;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private List<Movie> movieList = new ArrayList<>();
    private Context context;

    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    /**
     * Creates a MovieAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        //private final TextView movieNameTextView;
        private final ImageView ivMovieThumbnail;

        public MovieAdapterViewHolder(View view) {
            super(view);
            ivMovieThumbnail = view.findViewById(R.id.iv_movie_item);
            view.setOnClickListener(this);
            context = view.getContext();
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
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
        MovieAdapterViewHolder movieAdapterViewHolder = new MovieAdapterViewHolder(view);
        return movieAdapterViewHolder;
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param movieAdapterViewHolder The ViewHolder which should be updated to represent the
     *                               contents of the item at the given position in the data set.
     * @param position               The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {
        String movieThumbnail = movieList.get(position).getThumbnailPath();
        Picasso.with(context).load(NetworkUtils.buildUrl(movieThumbnail).toString()).into(movieAdapterViewHolder.ivMovieThumbnail);
    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
        if (movieList.isEmpty()) return 0;
        return movieList.size();
    }

    /**
     * This method is used to set the weather forecast on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new MovieAdapter to display it.
     *
     * @param movieData The new weather data to be displayed.
     */
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