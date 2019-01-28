package com.pri.movielistsample.view

import android.app.SearchManager
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.pri.movielistsample.R
import com.pri.movielistsample.databinding.ActivitySearchBinding
import com.pri.movielistsample.model.Movie
import com.pri.movielistsample.model.MovieListResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchResultActivity : BaseActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@SearchResultActivity, R.layout.activity_search)
        setSupportActionBar(binding.toolbar)
        if (intent != null) {
            handleIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        handleIntent(intent)
    }

    /**
     * Assuming this activity was started with a new intent, process the incoming information and
     * react accordingly.
     * @param intent
     */
    private fun handleIntent(intent: Intent) {
        // Special processing of the incoming intent only occurs if the if the action specified
        // by the intent is ACTION_SEARCH.
        if (Intent.ACTION_SEARCH == intent.action) {
            // SearchManager.QUERY is the key that a SearchManager will use to send a query string
            // to an Activity.
            val query = intent.getStringExtra(SearchManager.QUERY)

           /* // We need to create a bundle containing the query string to send along to the
            // LoaderManager, which will be handling querying the database and returning results.
            val bundle = Bundle()
            bundle.putString(QUERY_KEY, query)

            val loaderCallbacks = ContactablesLoaderCallbacks(this)

            // Start the loader with the new query, and an object that will handle all callbacks.
            loaderManager.restartLoader(CONTACT_QUERY_LOADER, bundle, loaderCallbacks)*/
            searchMovieList(query)
        }
    }

    fun searchMovieList(query: String?) {
        callRetrofit(true).searchMovieList(query).enqueue(object : Callback<MovieListResponseModel> {
            override fun onResponse(call: Call<MovieListResponseModel>, response: Response<MovieListResponseModel>) {
                try {
                    dismissProgressDialog()
                    val responseModel: MovieListResponseModel = response.body()!!
                    val movieList: List<Movie> = responseModel.movieList
                    if (movieList != null && movieList.size > 0) {
//                        listLoadedListener!!.onMovieListLoaded(movieList)
                        val adapter = MovieSearchAdapter(movieList)
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
                showErrorSnack(t.message!!)
            }
        })

    }


}
