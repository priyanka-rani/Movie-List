package com.pri.movielistsample.util;

public interface MovieListFetchListener {
    void onMovieListFetched(String endPoint, int page, MovieListLoadedListener listLoadedListener);
}
