package com.example.pathaoltd.movielistsample.viewmodel

import com.example.pathaoltd.movielistsample.model.Movie


class MovieListViewModel(var title: String, var endPointUrl: String){
    var movieList:List<Movie> = ArrayList()
    var totalPage:Int = 0
}