package com.landomen.mymovies.data.models;

/**
 * Object that represents simple tables in the database.
 */
public class SimpleObject {
    private int id = -1;
    private String title;

    public SimpleObject() {

    }

    public SimpleObject(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
