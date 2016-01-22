package com.landomen.mymovies.data.models;

import java.util.List;

/**
 * Object that represents a Movie table from the database.
 * Can be sorted by title.
 */
public class Movie implements Comparable {
    private int id;
    private String imdbId;
    private String title;
    private int year;
    private int runtime;
    private int metascore;
    private float imdbRating;
    private String plot;
    private String poster;
    private boolean watched;
    private SimpleObject rating;
    private List<SimpleObject> genres;
    private List<SimpleObject> actors;
    private List<SimpleObject> directors;
    private List<SimpleObject> writers;
    private List<SimpleObject> languages;
    private List<SimpleObject> countries;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getMetascore() {
        return metascore;
    }

    public void setMetascore(int metascore) {
        this.metascore = metascore;
    }

    public float getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(float imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public SimpleObject getRating() {
        return rating;
    }

    public void setRating(SimpleObject rating) {
        this.rating = rating;
    }

    public List<SimpleObject> getGenres() {
        return genres;
    }

    public void setGenres(List<SimpleObject> genres) {
        this.genres = genres;
    }

    public List<SimpleObject> getActors() {
        return actors;
    }

    public void setActors(List<SimpleObject> actors) {
        this.actors = actors;
    }

    public List<SimpleObject> getDirectors() {
        return directors;
    }

    public void setDirectors(List<SimpleObject> directors) {
        this.directors = directors;
    }

    public List<SimpleObject> getWriters() {
        return writers;
    }

    public void setWriters(List<SimpleObject> writers) {
        this.writers = writers;
    }

    public List<SimpleObject> getLanguages() {
        return languages;
    }

    public void setLanguages(List<SimpleObject> languages) {
        this.languages = languages;
    }

    public List<SimpleObject> getCountries() {
        return countries;
    }

    public void setCountries(List<SimpleObject> countries) {
        this.countries = countries;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    @Override
    public int compareTo(Object another) {
        return this.getTitle().compareTo(((Movie) another).getTitle());
    }
}
