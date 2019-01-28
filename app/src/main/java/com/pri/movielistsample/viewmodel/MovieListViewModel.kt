package com.pri.movielistsample.viewmodel

import com.pri.movielistsample.model.Movie


class MovieListViewModel(var title: String, var endPointUrl: String){
    var movieList:List<Movie> = ArrayList()
    var totalPage:Int = 0
}