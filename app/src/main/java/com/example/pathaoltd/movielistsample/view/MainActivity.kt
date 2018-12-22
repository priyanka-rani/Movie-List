package com.example.pathaoltd.movielistsample.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import com.example.pathaoltd.movielistsample.R
import com.example.pathaoltd.movielistsample.databinding.ActivityMainBinding
import com.example.pathaoltd.movielistsample.model.Movie
import com.example.pathaoltd.movielistsample.model.MovieListResponseModel
import com.example.pathaoltd.movielistsample.network.ApiUtils
import com.example.pathaoltd.movielistsample.util.MovieListFetchListener
import com.example.pathaoltd.movielistsample.util.MovieListLoadedListener
import com.example.pathaoltd.movielistsample.viewmodel.MovieListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import android.databinding.adapters.SearchViewBindingAdapter.setOnQueryTextListener
import android.view.View
import android.widget.SearchView


class MainActivity : BaseActivity(), MovieListFetchListener{
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        loadMovieList()
        binding.simpleSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(baseContext, query, Toast.LENGTH_LONG).show()
                searchMovieList(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Toast.makeText(baseContext, newText, Toast.LENGTH_LONG).show()
                searchMovieList(newText)
                return false
            }
        })
    }

    private fun loadMovieList() {
        val movieListList:ArrayList<MovieListViewModel> = ArrayList()
        movieListList.add(MovieListViewModel("TOP RATED", ApiUtils.TOP_RATED, 1, ArrayList<Movie>()))
        movieListList.add(MovieListViewModel("NOW PLAYING", ApiUtils.NOW_PLAYING, 1, ArrayList<Movie>()))
        movieListList.add(MovieListViewModel("UPCOMING", ApiUtils.UPCOMING, 1, ArrayList<Movie>()))

        val adapter:MovieListAdapter = MovieListAdapter(this@MainActivity, movieListList)
        binding.rvAlbumList.adapter = adapter

    }
    override fun onMovieListFetched(endPoint: String?, page: Int, listLoadedListener: MovieListLoadedListener?) {
        callRetrofit(true).getMovieList(endPoint, page).enqueue(object : Callback<MovieListResponseModel> {
            override fun onResponse(call: Call<MovieListResponseModel>, response: Response<MovieListResponseModel>) {
                try {
                    dismissProgressDialog()
                    val responseModel:MovieListResponseModel= response.body()!!
                    val movieList:List<Movie> = responseModel.movieList
                    if (movieList != null && movieList.size > 0) {
                        listLoadedListener!!.onMovieListLoaded(movieList)
                    } else {
                        showErrorSnack(response.message())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<MovieListResponseModel>, t: Throwable) {
                dismissProgressDialog()
                showErrorSnack(t.message!!)
            }
        })

    }

    fun searchMovieList(query: String?) {
        callRetrofit(false).searchMovieList(query).enqueue(object : Callback<MovieListResponseModel> {
            override fun onResponse(call: Call<MovieListResponseModel>, response: Response<MovieListResponseModel>) {
                try {
                    dismissProgressDialog()
                    val responseModel:MovieListResponseModel= response.body()!!
                    val movieList:List<Movie> = responseModel.movieList
                    if (movieList != null && movieList.size > 0) {
//                        listLoadedListener!!.onMovieListLoaded(movieList)
                        val adapter:MovieSearchAdapter = MovieSearchAdapter(this@MainActivity, movieList)
                        binding.rvSearchList.adapter = adapter
                        binding.rvAlbumList.visibility = View.INVISIBLE
                        binding.rvSearchList.visibility = View.VISIBLE

                    } else {
                        binding.rvAlbumList.visibility = View.VISIBLE
                        binding.rvSearchList.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<MovieListResponseModel>, t: Throwable) {
                dismissProgressDialog()
                showErrorSnack(t.message!!)
            }
        })

    }

}
