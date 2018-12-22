package com.example.pathaoltd.movielistsample.util;

import com.example.pathaoltd.movielistsample.model.Movie;

import java.util.List;

public interface MovieListFetchListener {
    void onMovieListFetched(String endPoint, int page, MovieListLoadedListener listLoadedListener);
}
