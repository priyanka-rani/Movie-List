package com.example.pathaoltd.movielistsample.network;

import com.example.pathaoltd.movielistsample.model.MovieListResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    // API to fetch movie list
    @GET("movie/{path}?api_key=4bc53930f5725a4838bf943d02af6aa9&language=en-US")
    Call<MovieListResponseModel> getMovieList(
            @Path("path") String path,
            @Query("page") int page
    );

    // API to search in movie list
    @GET("search/movie?api_key=4bc53930f5725a4838bf943d02af6aa9&language=en-US")
    Call<MovieListResponseModel> searchMovieList(
            @Query("query") String query
    );
}
