package com.example.pathaoltd.movielistsample.util;

import com.example.pathaoltd.movielistsample.model.MovieListResponseModel;

public interface MovieListLoadedListener {
    void onMovieListLoaded(MovieListResponseModel movieListResponse);
}
