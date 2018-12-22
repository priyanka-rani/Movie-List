package com.example.pathaoltd.movielistsample.util;

import com.example.pathaoltd.movielistsample.model.Movie;

import java.util.List;

public interface MovieListLoadedListener {
    void onMovieListLoaded(List<Movie> movieList);
}
