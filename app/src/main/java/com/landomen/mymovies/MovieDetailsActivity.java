package com.landomen.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.landomen.mymovies.data.MoviesDatabaseHelper;
import com.landomen.mymovies.data.models.Movie;
import com.landomen.mymovies.data.models.SimpleObject;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Shows details of the specific movie.
 */
public class MovieDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "com.landomen.mymovies.Movie";
    public static final String EXTRA_ID = "com.landomen.mymovies.Id";
    public static final String EXTRA_WATCHED = "com.landomen.mymovies.Watched";
    public static final String EXTRA_DELETED = "com.landomen.mymovies.Deleted";

    private Movie movie;
    private ImageView posterImage;
    private TextView titleText;
    private TextView yearText;
    private TextView lengthText;
    private TextView genresText;
    private TextView imdbText;
    private TextView metascoreText;
    private TextView languageText;
    private TextView directorText;
    private TextView ratingText;
    private TextView countryText;
    private TextView writersText;
    private TextView actorsText;
    private TextView plotText;

    private MenuItem menuWatched;
    private MenuItem menuNotWatched;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // get movie
        Intent intent = getIntent();
        if (intent != null) {
            String jsonMovie = intent.getStringExtra(EXTRA_MOVIE);
            if (jsonMovie != null) {
                try {
                    Gson gson = new Gson();
                    movie = gson.fromJson(jsonMovie, Movie.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    finish();
                }
            } else {
                finish();
            }
        }

        // set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(movie.getTitle());
        toolbar.inflateMenu(R.menu.menu_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        posterImage = (ImageView) findViewById(R.id.view_movie_poster);
        titleText = (TextView) findViewById(R.id.view_movie_title_text);
        yearText = (TextView) findViewById(R.id.view_movie_year_text);
        lengthText = (TextView) findViewById(R.id.view_movie_runtime_text);
        genresText = (TextView) findViewById(R.id.view_movie_genre_text);
        imdbText = (TextView) findViewById(R.id.view_movie_ratings_imdb_text);
        metascoreText = (TextView) findViewById(R.id.view_movie_ratings_metascore_text);
        languageText = (TextView) findViewById(R.id.view_movie_language_text);
        directorText = (TextView) findViewById(R.id.view_movie_director_text);
        ratingText = (TextView) findViewById(R.id.view_movie_rating_text);
        actorsText = (TextView) findViewById(R.id.view_movie_actors_text);
        plotText = (TextView) findViewById(R.id.view_movie_plot_text);
        countryText = (TextView) findViewById(R.id.view_movie_country_text);
        writersText = (TextView) findViewById(R.id.view_movie_writer_text);

        // display data
        Picasso.with(this).load(movie.getPoster()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(posterImage);
        titleText.setText(movie.getTitle());
        yearText.setText(String.valueOf(movie.getYear()));
        lengthText.setText(String.valueOf(movie.getRuntime()));
        imdbText.setText(String.valueOf(movie.getImdbRating()));
        metascoreText.setText(String.valueOf(movie.getMetascore()));
        ratingText.setText(movie.getRating().getTitle());
        plotText.setText(movie.getPlot());

        //Format the text for fields with multiple values
        List<SimpleObject> list = movie.getLanguages();
        String output = list.get(0).getTitle();
        for (int i = 1; i < list.size(); i++) {
            output += ",  " + list.get(i).getTitle();
        }
        languageText.setText(output);

        list = movie.getDirectors();
        output = list.get(0).getTitle();
        for (int i = 1; i < list.size(); i++) {
            output += ",  " + list.get(i).getTitle();
        }
        directorText.setText(output);

        list = movie.getActors();
        output = list.get(0).getTitle();
        for (int i = 1; i < list.size(); i++) {
            output += ",  " + list.get(i).getTitle();
        }
        actorsText.setText(output);

        list = movie.getWriters();
        output = list.get(0).getTitle();
        for (int i = 1; i < list.size(); i++) {
            output += ",  " + list.get(i).getTitle();
        }
        writersText.setText(output);

        list = movie.getCountries();
        output = list.get(0).getTitle();
        for (int i = 1; i < list.size(); i++) {
            output += ",  " + list.get(i).getTitle();
        }
        countryText.setText(output);

        list = movie.getGenres();
        output = list.get(0).getTitle();
        for (int i = 1; i < list.size(); i++) {
            output += ",  " + list.get(i).getTitle();
        }
        genresText.setText(output);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        menuWatched = menu.findItem(R.id.menu_details_seen);
        menuNotWatched = menu.findItem(R.id.menu_details_unseen);

        //set the Watched icon based on its status
        if (movie.isWatched()) {
            menuWatched.setVisible(false);
            menuNotWatched.setVisible(true);
        } else {
            menuWatched.setVisible(true);
            menuNotWatched.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exit(false);
                return true;
            case R.id.menu_details_delete:
                //Delete the movie
                if (MoviesDatabaseHelper.getInstance(this).deleteMovie(movie.getId())) {
                    exit(true);
                } else {
                    Snackbar.make(genresText, "Error deleting!", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_details_seen:
                if (MoviesDatabaseHelper.getInstance(this).updateSeenStatus(movie.getId(), true)) {
                    movie.setWatched(true);
                    Snackbar.make(genresText, "Movie marked as seen!", Snackbar.LENGTH_SHORT).show();
                    menuWatched.setVisible(false);
                    menuNotWatched.setVisible(true);
                }
                return true;
            case R.id.menu_details_unseen:
                if (MoviesDatabaseHelper.getInstance(this).updateSeenStatus(movie.getId(), false)) {
                    movie.setWatched(false);
                    Snackbar.make(genresText, "Movie marked as not seen!", Snackbar.LENGTH_SHORT).show();
                    menuWatched.setVisible(true);
                    menuNotWatched.setVisible(false);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void exit(boolean delete) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ID, movie.getId());
        intent.putExtra(EXTRA_WATCHED, movie.isWatched());
        intent.putExtra(EXTRA_DELETED, delete);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        exit(false);
    }
}
