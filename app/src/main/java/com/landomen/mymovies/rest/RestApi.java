package com.landomen.mymovies.rest;

import com.landomen.mymovies.rest.models.MovieResponse;
import com.landomen.mymovies.rest.models.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Defines the endpoints.
 */
public interface RestApi {

    @GET("/?r=json&type=movie&page=1")
    Call<SearchResponse> search(@Query("s") String searchString);

    @GET("/?r=json&type=movie&page=1")
    Call<SearchResponse> searchWithYear(@Query("s") String searchString, @Query("y") int year);

    @GET("/?plot=short&r=json")
    Call<MovieResponse> getMovie(@Query("i") String imdbId);
}
