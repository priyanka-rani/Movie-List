package com.example.pathaoltd.movielistsample.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.pathaoltd.movielistsample.R;
import com.example.pathaoltd.movielistsample.databinding.ItemMainBinding;
import com.example.pathaoltd.movielistsample.model.MovieListResponseModel;
import com.example.pathaoltd.movielistsample.util.MovieListFetchListener;
import com.example.pathaoltd.movielistsample.util.MovieListLoadedListener;
import com.example.pathaoltd.movielistsample.viewmodel.MovieListViewModel;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private List<MovieListViewModel> data;
    private MovieListFetchListener listener;

    public MovieListAdapter(MovieListFetchListener listener, List<MovieListViewModel> data) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMainBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_main, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ItemMainBinding binding = holder.binding;
        final MovieListViewModel movieListViewModel = data.get(position);
        binding.setData(movieListViewModel);
        if (movieListViewModel.getMovieList().isEmpty()) {
            listener.onMovieListFetched(movieListViewModel.getEndPointUrl(), 1, new MovieListLoadedListener() {
                @Override
                public void onMovieListLoaded(MovieListResponseModel movieListResponse) {
                    movieListViewModel.setMovieList(movieListResponse.getMovieList());
                    movieListViewModel.setTotalPage(movieListResponse.getTotalPages());
                    MovieAdapter movieAdapter = new MovieAdapter(listener, movieListViewModel);
                    binding.rvMainList.setAdapter(movieAdapter);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemMainBinding binding;

        ViewHolder(ItemMainBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
