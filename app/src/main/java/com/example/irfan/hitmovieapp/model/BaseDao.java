package com.example.irfan.hitmovieapp.model;

/**
 * Created by irfan on 05/05/16.
 */
public class BaseDao<T> {
    public int page;
    public int total_results;
    public T results;
    public int total_pages;

    public int getPage() {
        return page;
    }

    public T getResults() {
        return results;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }
}
