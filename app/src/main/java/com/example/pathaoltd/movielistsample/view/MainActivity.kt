package com.example.pathaoltd.movielistsample.view

import android.app.SearchManager
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.pathaoltd.movielistsample.R
import com.example.pathaoltd.movielistsample.databinding.ActivityMainBinding
import com.example.pathaoltd.movielistsample.model.Movie
import com.example.pathaoltd.movielistsample.model.MovieListResponseModel
import com.example.pathaoltd.movielistsample.network.ApiUtils
import com.example.pathaoltd.movielistsample.util.MovieListFetchListener
import com.example.pathaoltd.movielistsample.util.MovieListLoadedListener
import com.example.pathaoltd.movielistsample.util.Utils
import com.example.pathaoltd.movielistsample.viewmodel.MovieListViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule




class MainActivity : BaseActivity(), MovieListFetchListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        loadMovieList()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName))

        // When using the support library, the setOnActionExpandListener() method is
// static and accepts the MenuItem object as an argument
        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.search), object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                //the searchview has been closed
                binding.rvAlbumList.visibility = View.VISIBLE
                binding.rvSearchList.visibility = View.GONE
                return true  // Return true to collapse action view
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                binding.rvAlbumList.visibility = View.GONE
                return true  // Return true to expand action view
            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            var timer = Timer()

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                timer.cancel()
                val sleep = when(newText.length) {
                    1 -> 1000L
                    2,3 -> 700L
                    4,5 -> 500L
                    else -> 300L
                }
                timer = Timer()
                timer.schedule(sleep) {
                    if (!newText.isNullOrEmpty()) {
                        // search
                        searchMovieList(newText)
                    }
                }
                return true
            }
        })

        return true
    }

    private fun loadMovieList() {
        val movieListList: ArrayList<MovieListViewModel> = ArrayList()
        movieListList.add(MovieListViewModel("TOP RATED", ApiUtils.TOP_RATED))
        movieListList.add(MovieListViewModel("NOW PLAYING", ApiUtils.NOW_PLAYING))
        movieListList.add(MovieListViewModel("UPCOMING", ApiUtils.UPCOMING))

        val adapter = MovieListAdapter(this@MainActivity, movieListList)
        binding.rvAlbumList.adapter = adapter

    }

    override fun onMovieListFetched(endPoint: String?, page: Int, listLoadedListener: MovieListLoadedListener?) {
        callRetrofit(page ==1).getMovieList(endPoint, page).enqueue(object :
                Callback<MovieListResponseModel> {
            override fun onResponse(call: Call<MovieListResponseModel>, response: Response<MovieListResponseModel>) {
                try {
                    dismissProgressDialog()
                    val responseModel: MovieListResponseModel = response.body()!!
                    val movieList: List<Movie> = responseModel.movieList
                    if (movieList != null && movieList.size > 0) {
                        listLoadedListener!!.onMovieListLoaded(responseModel)
                    } else {
                        showErrorSnack(response.message())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<MovieListResponseModel>, t: Throwable) {
                dismissProgressDialog()
                if(Utils.isOnline(this@MainActivity))
                    showErrorSnack(t.message!!)
                else{
                    showErrorSnack(getString(R.string.no_internet))
                }
            }
        })

    }
    fun searchMovieList(query: String?) {
        callRetrofit(true).searchMovieList(query).enqueue(object : Callback<MovieListResponseModel> {
            override fun onResponse(call: Call<MovieListResponseModel>, response: Response<MovieListResponseModel>) {
                try {
                    dismissProgressDialog()
                    val responseModel:MovieListResponseModel= response.body()!!
                    val movieList:List<Movie> = responseModel.movieList
                    if (movieList != null && movieList.size > 0) {
//                        listLoadedListener!!.onMovieListLoaded(movieList)
                        val adapter:MovieSearchAdapter = MovieSearchAdapter(movieList)
                        binding.rvSearchList.adapter = adapter
                        binding.rvSearchList.visibility = View.VISIBLE

                    } else {
                        binding.rvSearchList.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<MovieListResponseModel>, t: Throwable) {
                dismissProgressDialog()
                if(Utils.isOnline(this@MainActivity))
                showErrorSnack(t.message!!)
                else{
                    showErrorSnack(getString(R.string.no_internet))
                }
            }
        })

    }

    override fun showProgressDialog(isCancelable: Boolean) {
        runOnUiThread {
            try {
                binding.progressBar.visibility = View.VISIBLE
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun dismissProgressDialog() {
        runOnUiThread {
            try {
                binding.progressBar.visibility = View.GONE
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
