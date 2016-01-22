package com.landomen.mymovies.rest.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a JSON when downloading details for a specific movie.
 */
public class MovieResponse {
    @SerializedName("Title")

    private String Title;
    @SerializedName("Year")

    private String Year;
    @SerializedName("Rated")

    private String Rated;
    @SerializedName("Released")

    private String Released;
    @SerializedName("Runtime")

    private String Runtime;
    @SerializedName("Genre")

    private String Genre;
    @SerializedName("Director")

    private String Director;
    @SerializedName("Writer")

    private String Writer;
    @SerializedName("Actors")

    private String Actors;
    @SerializedName("Plot")

    private String Plot;
    @SerializedName("Language")

    private String Language;
    @SerializedName("Country")

    private String Country;
    @SerializedName("Awards")

    private String Awards;
    @SerializedName("Poster")

    private String Poster;
    @SerializedName("Metascore")

    private String Metascore;
    @SerializedName("imdbRating")

    private String imdbRating;
    @SerializedName("imdbVotes")

    private String imdbVotes;
    @SerializedName("imdbID")

    private String imdbID;
    @SerializedName("Type")

    private String Type;
    @SerializedName("Response")

    private String Response;

    /**
     * @return The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return The Year
     */
    public String getYear() {
        return Year;
    }

    /**
     * @param Year The Year
     */
    public void setYear(String Year) {
        this.Year = Year;
    }

    /**
     * @return The Rated
     */
    public String getRated() {
        return Rated;
    }

    /**
     * @param Rated The Rated
     */
    public void setRated(String Rated) {
        this.Rated = Rated;
    }

    /**
     * @return The Released
     */
    public String getReleased() {
        return Released;
    }

    /**
     * @param Released The Released
     */
    public void setReleased(String Released) {
        this.Released = Released;
    }

    /**
     * @return The Runtime
     */
    public String getRuntime() {
        return Runtime;
    }

    /**
     * @param Runtime The Runtime
     */
    public void setRuntime(String Runtime) {
        this.Runtime = Runtime;
    }

    /**
     * @return The Genre
     */
    public String getGenre() {
        return Genre;
    }

    /**
     * @param Genre The Genre
     */
    public void setGenre(String Genre) {
        this.Genre = Genre;
    }

    /**
     * @return The Director
     */
    public String getDirector() {
        return Director;
    }

    /**
     * @param Director The Director
     */
    public void setDirector(String Director) {
        this.Director = Director;
    }

    /**
     * @return The Writer
     */
    public String getWriter() {
        return Writer;
    }

    /**
     * @param Writer The Writer
     */
    public void setWriter(String Writer) {
        this.Writer = Writer;
    }

    /**
     * @return The Actors
     */
    public String getActors() {
        return Actors;
    }

    /**
     * @param Actors The Actors
     */
    public void setActors(String Actors) {
        this.Actors = Actors;
    }

    /**
     * @return The Plot
     */
    public String getPlot() {
        return Plot;
    }

    /**
     * @param Plot The Plot
     */
    public void setPlot(String Plot) {
        this.Plot = Plot;
    }

    /**
     * @return The Language
     */
    public String getLanguage() {
        return Language;
    }

    /**
     * @param Language The Language
     */
    public void setLanguage(String Language) {
        this.Language = Language;
    }

    /**
     * @return The Country
     */
    public String getCountry() {
        return Country;
    }

    /**
     * @param Country The Country
     */
    public void setCountry(String Country) {
        this.Country = Country;
    }

    /**
     * @return The Awards
     */
    public String getAwards() {
        return Awards;
    }

    /**
     * @param Awards The Awards
     */
    public void setAwards(String Awards) {
        this.Awards = Awards;
    }

    /**
     * @return The Poster
     */
    public String getPoster() {
        return Poster;
    }

    /**
     * @param Poster The Poster
     */
    public void setPoster(String Poster) {
        this.Poster = Poster;
    }

    /**
     * @return The Metascore
     */
    public String getMetascore() {
        return Metascore;
    }

    /**
     * @param Metascore The Metascore
     */
    public void setMetascore(String Metascore) {
        this.Metascore = Metascore;
    }

    /**
     * @return The imdbRating
     */
    public String getImdbRating() {
        return imdbRating;
    }

    /**
     * @param imdbRating The imdbRating
     */
    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    /**
     * @return The imdbVotes
     */
    public String getImdbVotes() {
        return imdbVotes;
    }

    /**
     * @param imdbVotes The imdbVotes
     */
    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    /**
     * @return The imdbID
     */
    public String getImdbID() {
        return imdbID;
    }

    /**
     * @param imdbID The imdbID
     */
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    /**
     * @return The Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type The Type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    /**
     * @return The Response
     */
    public String getResponse() {
        return Response;
    }

    /**
     * @param Response The Response
     */
    public void setResponse(String Response) {
        this.Response = Response;
    }
}
