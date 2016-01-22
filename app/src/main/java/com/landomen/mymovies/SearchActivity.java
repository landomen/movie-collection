package com.landomen.mymovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import com.landomen.mymovies.data.MoviesDatabaseHelper;
import com.landomen.mymovies.data.models.SimpleObject;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_TITLE = "emp.fri.mymovie.SearchActivity.Title";
    public static final String EXTRA_YEAR = "emp.fri.mymovie.SearchActivity.Year";
    public static final String EXTRA_GENRE = "emp.fri.mymovie.SearchActivity.Genre";


    private EditText titleEditText;
    private Button searchButton;
    private Spinner genreSpinner;
    private Spinner yearSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        titleEditText = (EditText) findViewById(R.id.search_movies_title_edit_text);
        searchButton = (Button) findViewById(R.id.search_movies_search_button);
        genreSpinner = (Spinner) findViewById(R.id.search_movies_genre_spinner);
        yearSpinner = (Spinner) findViewById(R.id.search_movies_year_spinner);
        searchButton.setOnClickListener(this);

        //years
        List<SimpleObject> years = new ArrayList<>();
        years.add(new SimpleObject(0, "All decades"));
        for (int i = 1910; i < 2020; i += 10) {
            years.add(new SimpleObject(i, String.format("%d - %d", i, i + 9)));
        }

        ArrayAdapter<SimpleObject> yearAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        //genres
        List<SimpleObject> genres = new ArrayList<>();
        genres.add(new SimpleObject(0, "All genres"));
        genres.addAll(MoviesDatabaseHelper.getInstance(this).getGenres());
        ArrayAdapter<SimpleObject> genreAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, genres);
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreAdapter);
    }

    @Override
    public void onClick(View v) {
        String title = titleEditText.getText().toString();
        SimpleObject year = (SimpleObject) yearSpinner.getSelectedItem();
        SimpleObject genre = (SimpleObject) genreSpinner.getSelectedItem();

        if (title.isEmpty() && year.getId() == 0 && genre.getId() == 0) {
            Snackbar.make(titleEditText, "Missing search parameters!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TITLE, title);
        intent.putExtra(EXTRA_YEAR, year.getId());
        intent.putExtra(EXTRA_GENRE, genre.getId());
        setResult(RESULT_OK, intent);
        finish();
    }
}
