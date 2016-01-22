package com.landomen.mymovies.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

import com.landomen.mymovies.R;
import com.landomen.mymovies.data.MoviesDatabaseHelper;
import com.landomen.mymovies.rest.RestApi;
import com.landomen.mymovies.rest.RetrofitManager;
import com.landomen.mymovies.rest.models.MovieResponse;
import com.landomen.mymovies.rest.models.Search;
import com.landomen.mymovies.rest.models.SearchResponse;
import com.landomen.mymovies.search.models.SearchListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Performs a call to the API and displays the search results.
 * Results are returned to AddActivity upon selection.
 */
public class SearchResultsActivity extends AppCompatActivity implements Callback<SearchResponse> {
    private static final String TAG = "SearchResultsActivity";

    public static final int REQUEST_CODE = 625;
    public static final String EXTRA_TITLE = "com.landomen.mymovies.search.Title";
    public static final String EXTRA_YEAR = "com.landomen.mymovies.search.Year";
    public static final String EXTRA_RESULT = "com.landomen.mymovies.search.Result";

    private ListView listView;
    private TextView messageTV;
    private ProgressBar progressBar;
    private LinearLayout buttonsLayout;

    private int moviesAdded = 0;
    private int movieRequests = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        listView = (ListView) findViewById(R.id.search_results_list_view);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        messageTV = (TextView) findViewById(R.id.search_results_tv);
        progressBar = (ProgressBar) findViewById(R.id.search_results_progress_bar);
        buttonsLayout = (LinearLayout) findViewById(R.id.search_results_buttons);

        Intent intent = getIntent();
        if (intent == null)
            finish();

        String title = intent.getStringExtra(EXTRA_TITLE);
        if (title == null)
            finish();
        int year = intent.getIntExtra(EXTRA_YEAR, 0);

        showLoading(true);

        Call<SearchResponse> searchCall = null;
        if (year == 0) {
            searchCall = RetrofitManager.getInstance().search(title);
        } else {
            searchCall = RetrofitManager.getInstance().searchWithYear(title, year);
        }
        searchCall.enqueue(this);

    }

    /**
     * Shows/hides loading bar while executing async call.
     *
     * @param show
     */
    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onResponse(Response<SearchResponse> response) {
        if (response != null && response.body() != null) {
            SearchResponse movieList = response.body();

            //remove saved movies
            MoviesDatabaseHelper databaseHelper = MoviesDatabaseHelper.getInstance(SearchResultsActivity.this);
            Iterator<Search> searchIterator = movieList.getSearch().iterator();
            while (searchIterator.hasNext()) {
                Search s = searchIterator.next();
                if (databaseHelper.doesMovieExist(s.getImdbID()))
                    searchIterator.remove();
            }

            if (movieList.getSearch().size() == 0) {
                showMessage(R.string.search_no_movies);
            } else {
                listView.setAdapter(new SearchListAdapter(SearchResultsActivity.this, movieList));
                showLoading(false);
                listView.setVisibility(View.VISIBLE);
                buttonsLayout.setVisibility(View.VISIBLE);
            }
        } else {
            showMessage(R.string.search_error);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        showMessage(R.string.search_error);
    }


    private void showMessage(int resId) {
        showLoading(false);
        messageTV.setVisibility(View.VISIBLE);
        messageTV.setText(resId);
    }

    /**
     * Save all the selected movies to database.
     *
     * @param view
     */
    public void onSave(View view) {
        final List<String> selectedItemsIds = ((SearchListAdapter) listView.getAdapter()).getSelectedItemsIds();
        if (selectedItemsIds.size() == 0) {
            Snackbar.make(buttonsLayout, R.string.search_save_nothing, Snackbar.LENGTH_SHORT).show();
            return;
        }

        //show saving feedback
        messageTV.setVisibility(View.VISIBLE);
        messageTV.setText(R.string.search_saving);
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        buttonsLayout.setVisibility(View.GONE);

        RestApi restApi = RetrofitManager.getInstance();
        Call<MovieResponse> movieCall;
        for (String imdbId :
                selectedItemsIds) {
            movieCall = restApi.getMovie(imdbId);
            movieCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Response<MovieResponse> response) {
                    if (response != null && response.body() != null) {
                        if (MoviesDatabaseHelper.getInstance(SearchResultsActivity.this).addMovie(response.body())) {
                            Log.e(TAG, "Movie added!");
                            moviesAdded++;
                        } else {
                            Log.e(TAG, "Movie not added!");
                        }
                    } else {
                        Log.e(TAG, "No movie from server!");
                    }
                    movieRequests++;
                    if (movieRequests == selectedItemsIds.size()) {
                        onFinishedDownloading();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e(TAG, "No movie from server!");
                    movieRequests++;
                    if (movieRequests == selectedItemsIds.size()) {
                        onFinishedDownloading();
                    }
                }
            });
        }
    }

    /**
     * When all movies have been downloaded.
     * Return to Add Activity.
     */
    private void onFinishedDownloading() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(EXTRA_RESULT, moviesAdded);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    /**
     * Clears the list view selection.
     *
     * @param view
     */
    public void onCancel(View view) {
        ((SearchListAdapter) listView.getAdapter()).removeSelection();
    }
}
