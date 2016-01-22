package com.landomen.mymovies.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Contains a single instance of the Retrofit object.
 */
public class RetrofitManager {
    private static final String BASE_URL = " http://www.omdbapi.com/";

    private static RestApi restApi;

    public static synchronized RestApi getInstance() {
        if (restApi == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("dd MMM yyyy")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            restApi = retrofit.create(RestApi.class);
        }
        return restApi;
    }
}
