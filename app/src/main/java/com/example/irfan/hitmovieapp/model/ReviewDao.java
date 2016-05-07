package com.example.irfan.hitmovieapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by irfan on 07/05/16.
 */
public class ReviewDao implements Serializable {
    public int id;
    public int page;
    public List<Results> results;
    public int total_pages;
    public int total_results;

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public List<Results> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public class Results implements Serializable {
        public String id;
        public String author;
        public String content;
        public String url;

        public String getId() {
            return id;
        }

        public String getAuthor() {
            return author;
        }

        public String getContent() {
            return content;
        }

        public String getUrl() {
            return url;
        }
    }
}
