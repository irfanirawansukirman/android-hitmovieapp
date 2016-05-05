package com.example.irfan.hitmovieapp.controller;

import com.example.irfan.hitmovieapp.model.BaseDao;
import com.example.irfan.hitmovieapp.model.MovieDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by irfan on 05/05/16.
 */
public interface MovieService {
    /**
     * GET LIST BY POPULARITY
     */
    @GET("/3/discover/movie?api_key=1b2f29d43bf2e4f3142530bc6929d341&sort_by=popularity.desc")
    Call<BaseDao<List<MovieDao>>> callMoviePopular();
}
