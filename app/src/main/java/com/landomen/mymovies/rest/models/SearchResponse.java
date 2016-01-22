package com.landomen.mymovies.rest.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the JSON response when searching.
 */
public class SearchResponse {

    @SerializedName("Search")
    private List<Search> Search = new ArrayList<Search>();

    /**
     * @return The Search
     */
    public List<Search> getSearch() {
        return Search;
    }

    /**
     * @param Search The Search
     */
    public void setSearch(List<Search> Search) {
        this.Search = Search;
    }

}
