package com.landomen.mymovies.data.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.landomen.mymovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Custom RecyclerView Adapter for displaying movies on the front page.
 * Can dynamically update its content (add, remove items).
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private Context mContext;
    private static MovieClickListener movieClickListener;

    public MovieAdapter(Context mContext, List<Movie> movies) {
        this.movies = new ArrayList<>(movies);
        this.mContext = mContext;
    }

    /**
     * Holds all view references to Movie card.
     */
    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView mPoster;
        protected TextView mTitle;
        protected TextView mYear;
        protected TextView mRuntime;
        protected TextView mGenre;
        protected TextView mPlot;

        public MovieViewHolder(View view) {
            super(view);
            mPoster = (ImageView) view.findViewById(R.id.movie_list_row_poster);
            mTitle = (TextView) view.findViewById(R.id.movie_list_row_title_text);
            mYear = (TextView) view.findViewById(R.id.movie_list_row_info_year_text);
            mRuntime = (TextView) view.findViewById(R.id.movie_list_row_info_runtime_text);
            mGenre = (TextView) view.findViewById(R.id.movie_list_row_genre_text);
            mPlot = (TextView) view.findViewById(R.id.movie_list_row_plot_text);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            movieClickListener.onMovieClick(getAdapterPosition(), v);
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MovieViewHolder movieViewHolder, int i) {
        Movie movie = movies.get(i);

        Picasso.with(mContext).load(movie.getPoster()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(movieViewHolder.mPoster);
        movieViewHolder.mTitle.setText(movie.getTitle());
        movieViewHolder.mYear.setText(String.valueOf(movie.getYear()));
        movieViewHolder.mRuntime.setText(String.valueOf(movie.getRuntime()));
        List<SimpleObject> genres = movie.getGenres();
        StringBuilder sb = new StringBuilder(genres.get(0).getTitle());
        for (int j = 1; j < genres.size(); j++) {
            sb.append("  " + genres.get(j).getTitle());
        }
        movieViewHolder.mGenre.setText(sb.toString());
        movieViewHolder.mPlot.setText(movie.getPlot());
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }

    /**
     * Removes item from list.
     *
     * @param id
     */
    public void removeItem(int id) {
        Iterator<Movie> iterator = movies.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Movie m = iterator.next();
            if (m.getId() == id) {
                iterator.remove();
                break;
            }
            i++;
        }
        notifyItemRemoved(i);
    }

    /**
     * Mark movie as watched.
     *
     * @param id
     * @param watched
     */
    public void markMovieAsWatched(int id, boolean watched) {
        for (Movie m :
                movies) {
            if (m.getId() == id) {
                m.setWatched(watched);
            }
        }
    }

    /**
     * @return Number of items.
     */
    public int getItemCount() {
        return movies.size();
    }

    public void setOnMovieClickListener(MovieClickListener movieClickListener) {
        this.movieClickListener = movieClickListener;
    }

    /**
     * Add a new item to the list.
     *
     * @param movie
     */
    public void addItem(Movie movie) {
        movies.add(movie);
        notifyItemInserted(movies.size() - 1);
    }

    /**
     * Sort items
     *
     * @param ascending True/false
     */
    public void sortItems(boolean ascending) {
        if (!ascending)
            Collections.sort(movies, Collections.reverseOrder());
        else
            Collections.sort(movies);
        notifyDataSetChanged();
    }


    /**
     * Updates its list of items. New items are added and old ones are removed.
     *
     * @param newMovies
     */
    public void setItems(List<Movie> newMovies) {
        //remove old movies
        for (int i = this.movies.size() - 1; i >= 0; i--) {
            final Movie movie = this.movies.get(i);
            if (!newMovies.contains(movie)) {
                this.movies.remove(i);
                notifyItemRemoved(i);
            }
        }

        //add new movies
        for (int i = newMovies.size() - 1; i >= 0; i--) {
            final Movie movie = newMovies.get(i);
            if (!this.movies.contains(movie)) {
                addItem(movie);
            }
        }
    }


    /**
     * Interface for movie callbacks.
     */
    public interface MovieClickListener {
        void onMovieClick(int position, View v);
    }
}
