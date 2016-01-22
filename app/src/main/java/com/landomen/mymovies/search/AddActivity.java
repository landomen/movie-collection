package com.landomen.mymovies.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.landomen.mymovies.R;
import com.landomen.mymovies.rest.models.SearchResponse;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * This is where users search queries are parsed.
 */
public class AddActivity extends AppCompatActivity implements Callback<SearchResponse> {

    private EditText titleEditText;
    private EditText yearEditText;
    private RelativeLayout mainLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        titleEditText = (EditText) findViewById(R.id.add_movie_title_edit_text);
        yearEditText = (EditText) findViewById(R.id.add_movie_year_edit_text);
        mainLayout = (RelativeLayout) findViewById(R.id.add_movie_main_layout);
        progressBar = (ProgressBar) findViewById(R.id.add_movie_progress_bar);
    }


    public void onSearch(View view) {
        String title = titleEditText.getText().toString().trim();
        String year = yearEditText.getText().toString().trim();

        // check if input is correct
        if (title.isEmpty()) {
            titleEditText.setError(getString(R.string.search_title_empty));
            if (!year.isEmpty() && year.length() < 4) {
                yearEditText.setError(getString(R.string.search_year_wrong));
            }
            return;
        }
        if (!year.isEmpty() && year.length() < 4) {
            yearEditText.setError(getString(R.string.search_year_wrong));
            return;
        }


        //start search activity
        Intent startSearch = new Intent(this, SearchResultsActivity.class);
        startSearch.putExtra(SearchResultsActivity.EXTRA_TITLE, title);
        startSearch.putExtra(SearchResultsActivity.EXTRA_YEAR, year.isEmpty() ? 0 : Integer.parseInt(year));
        startActivityForResult(startSearch, SearchResultsActivity.REQUEST_CODE);
    }

    /**
     * Shows/hides loading bar while executing async call.
     *
     * @param show
     */
    private void showLoading(boolean show) {
        mainLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onResponse(Response<SearchResponse> response) {
        if (response != null && response.body() != null) {
            SearchResponse movieList = response.body();
            if (movieList.getSearch().size() == 0) {
                showLoading(false);
                Snackbar.make(mainLayout, "No movies found", Snackbar.LENGTH_SHORT).show();
            } else {
                showLoading(false);
                Snackbar.make(mainLayout, "Found", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            showLoading(false);
            Snackbar.make(mainLayout, "Error searching", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Throwable t) {
        showLoading(false);
        Snackbar.make(mainLayout, "Error searching", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SearchResultsActivity.REQUEST_CODE && data != null) {
            int result = data.getIntExtra(SearchResultsActivity.EXTRA_RESULT, 0);
            if (result > 0) {
                Snackbar.make(progressBar, "Movies saved: " + result, Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(progressBar, "Error saving movies!", Snackbar.LENGTH_LONG).show();
            }
        }
    }
}
