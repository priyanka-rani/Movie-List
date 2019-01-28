package com.pri.movielistsample.util;

import com.pri.movielistsample.model.MovieListResponseModel;

public interface MovieListLoadedListener {
    void onMovieListLoaded(MovieListResponseModel movieListResponse);
}
