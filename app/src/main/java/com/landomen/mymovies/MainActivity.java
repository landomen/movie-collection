package com.landomen.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.landomen.mymovies.data.MoviesDatabaseHelper;
import com.landomen.mymovies.data.models.Movie;
import com.landomen.mymovies.data.models.MovieAdapter;
import com.landomen.mymovies.search.AddActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Displays the list of movies that are stored in the database.
 */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private static final int DETAILS_RQC = 856;
    private static final int SEARCH_RQC = 764;

    private List<Movie> movies;
    private TextView noMoviesText;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MovieAdapter mAdapter;
    private FloatingActionButton fab;
    private RadioGroup watchedOption;
    private Toolbar toolbar;
    private boolean ascSort = true;
    private MenuItem searchItem;
    private MenuItem cancelItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        noMoviesText = (TextView) findViewById(R.id.movies_list_no_movies_text);
        watchedOption = (RadioGroup) findViewById(R.id.movie_list_bottom_toolbar_watched_radio);
        watchedOption.setOnCheckedChangeListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_list_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //get movies from the database
        movies = MoviesDatabaseHelper.getInstance(this).getMovies(null);
        //show the number of movies
        toolbar.setSubtitle(movies.size() + " movies");
        //create the RecyclerView adapter
        mAdapter = new MovieAdapter(this, movies);
        //If there are no movies, show a message.
        if (movies.size() != 0) {
            mAdapter.setOnMovieClickListener(new MovieAdapter.MovieClickListener() {
                @Override
                public void onMovieClick(int position, View v) {
                    Movie selectedMovie = mAdapter.getItem(position);
                    Gson gson = new Gson();
                    String jsonMovie = gson.toJson(selectedMovie);
                    Intent openDetails = new Intent(MainActivity.this, MovieDetailsActivity.class);
                    openDetails.putExtra(MovieDetailsActivity.EXTRA_MOVIE, jsonMovie);
                    startActivityForResult(openDetails, DETAILS_RQC);
                }
            });
            mRecyclerView.setAdapter(mAdapter);
            watchedOption.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.GONE);
            noMoviesText.setVisibility(View.VISIBLE);
            watchedOption.setVisibility(View.GONE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.action_search);
        cancelItem = menu.findItem(R.id.action_cancel);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            //open search
            startActivityForResult(new Intent(MainActivity.this, SearchActivity.class), SEARCH_RQC);
            return true;
        } else if (id == R.id.action_order) {
            ascSort = !ascSort;
            mAdapter.sortItems(ascSort);
            return true;
        } else if (id == R.id.action_cancel) {
            searchItem.setVisible(true);
            cancelItem.setVisible(false);
            if (movies.size() > 0) {
                mAdapter.setItems(movies);
                watchedOption.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                noMoviesText.setVisibility(View.GONE);
            } else {
                watchedOption.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                noMoviesText.setVisibility(View.VISIBLE);
            }
            toolbar.setTitle(R.string.app_name);
            toolbar.setSubtitle(mAdapter.getItemCount() + " movies");
            fab.setVisibility(View.VISIBLE);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAILS_RQC && data != null) {
            int movieId = data.getIntExtra(MovieDetailsActivity.EXTRA_ID, 0);
            if (movieId > 0) {
                mAdapter.markMovieAsWatched(movieId, data.getBooleanExtra(MovieDetailsActivity.EXTRA_WATCHED, false));
                //delete
                if (data.getBooleanExtra(MovieDetailsActivity.EXTRA_DELETED, false)) {
                    //remove from dataset
                    Iterator<Movie> iterator = movies.iterator();
                    while (iterator.hasNext()) {
                        Movie m = iterator.next();
                        if (m.getId() == movieId) {
                            iterator.remove();
                            break;
                        }
                    }

                    mAdapter.removeItem(movieId);
                    toolbar.setSubtitle(mAdapter.getItemCount() + " movies");
                    Snackbar.make(fab, "Movie removed!", Snackbar.LENGTH_SHORT).show();
                    //no movies left
                    if (mAdapter.getItemCount() == 0) {
                        mRecyclerView.setVisibility(View.GONE);
                        noMoviesText = (TextView) findViewById(R.id.movies_list_no_movies_text);
                        noMoviesText.setVisibility(View.VISIBLE);
                        watchedOption.setVisibility(View.GONE);
                    }
                }
            }
        } else if (requestCode == SEARCH_RQC && data != null) {
            String title = data.getStringExtra(SearchActivity.EXTRA_TITLE);
            int yearId = data.getIntExtra(SearchActivity.EXTRA_YEAR, 0);
            int genreId = data.getIntExtra(SearchActivity.EXTRA_GENRE, 0);

            List<Movie> resultMovies = new ArrayList<>(movies);
            if (!title.isEmpty()) {
                resultMovies = MoviesDatabaseHelper.getInstance(this).getMovies(title);
            }

            List<Movie> filterMovies = new ArrayList<>();
            for (Movie m : resultMovies) {
                //only year
                if (yearId != 0 && genreId == 0) {
                    if (m.getYear() >= yearId && m.getYear() <= yearId + 9) {
                        filterMovies.add(m);
                    }
                } else if (yearId == 0 && genreId != 0) {
                    //only genre
                    for (int i = 0; i < m.getGenres().size(); i++) {
                        if (m.getGenres().get(i).getId() == genreId) {
                            filterMovies.add(m);
                            break;
                        }
                    }
                } else if (yearId != 0 && genreId != 0) {
                    //both
                    boolean hasYear = false;
                    if (m.getYear() >= yearId && m.getYear() <= yearId + 9) {
                        hasYear = true;
                    }
                    if (hasYear) {
                        for (int i = 0; i < m.getGenres().size(); i++) {
                            if (m.getGenres().get(i).getId() == genreId) {
                                filterMovies.add(m);
                                break;
                            }
                        }
                    }
                } else if (!title.isEmpty()) {
                    filterMovies.add(m);
                }
            }
            toolbar.setTitle("Results");
            toolbar.setSubtitle(filterMovies.size() + " movies");
            mAdapter.setItems(filterMovies);

            watchedOption.setVisibility(View.GONE);
            searchItem.setVisible(false);
            cancelItem.setVisible(true);

            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //all
            case R.id.movie_list_bottom_toolbar_watched_radio_1:
                mAdapter.setItems(movies);
                mAdapter.sortItems(ascSort);
                toolbar.setSubtitle(movies.size() + " movies");
                break;
            //watched
            case R.id.movie_list_bottom_toolbar_watched_radio_2:
                List<Movie> seenMovies = new ArrayList<>();
                for (Movie m : movies) {
                    if (m.isWatched())
                        seenMovies.add(m);
                }
                toolbar.setSubtitle(seenMovies.size() + " movies");
                mAdapter.setItems(seenMovies);
                mAdapter.sortItems(ascSort);
                break;
            //not watched
            case R.id.movie_list_bottom_toolbar_watched_radio_3:
                List<Movie> notSeenMovies = new ArrayList<>();
                for (Movie m : movies) {
                    if (!m.isWatched())
                        notSeenMovies.add(m);
                }
                toolbar.setSubtitle(notSeenMovies.size() + " movies");
                mAdapter.setItems(notSeenMovies);
                mAdapter.sortItems(ascSort);
                break;
        }
    }
}
