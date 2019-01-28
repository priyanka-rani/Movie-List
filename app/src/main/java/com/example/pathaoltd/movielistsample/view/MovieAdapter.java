package com.example.pathaoltd.movielistsample.view;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.pathaoltd.movielistsample.R;
import com.example.pathaoltd.movielistsample.databinding.ItemMovieBinding;
import com.example.pathaoltd.movielistsample.model.Movie;
import com.example.pathaoltd.movielistsample.model.MovieListResponseModel;
import com.example.pathaoltd.movielistsample.network.ApiUtils;
import com.example.pathaoltd.movielistsample.util.MovieListFetchListener;
import com.example.pathaoltd.movielistsample.util.MovieListLoadedListener;
import com.example.pathaoltd.movielistsample.viewmodel.MovieListViewModel;
import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private MovieListViewModel data;
    private MovieListFetchListener listener;
    boolean isLoading = false;

    private int visibleThreshold = 5;
    int curPage = 1;

    MovieAdapter(MovieListFetchListener listener, MovieListViewModel data) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ItemMovieBinding binding = holder.binding;
        if (position == data.getMovieList().size()) {
            binding.ivThumb.setImageResource(R.drawable.ic_load);
            binding.tvName.setText(R.string.loading);
            binding.tvYear.setText("");
        } else {
            Movie movie = data.getMovieList().get(position);
            binding.setData(movie);
            String url = ApiUtils.IMAGE_BASE_URL + movie.getBackdropPath();
            Picasso.get().load(url).into(binding.ivThumb);
        }
    }

    @Override
    public int getItemCount() {
        if (curPage < data.getTotalPage()) return data.getMovieList().size() + 1;
        else return data.getMovieList().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;

        ViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = linearLayoutManager.getItemCount();
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (listener != null) {
                        isLoading = true;
                        listener.onMovieListFetched(data.getEndPointUrl(), ++curPage, new MovieListLoadedListener() {
                            @Override
                            public void onMovieListLoaded(final MovieListResponseModel movieList) {

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //   remove progress item
                                        int prevSize = data.getMovieList().size();
                                        data.getMovieList().addAll(movieList.getMovieList());
                                        notifyItemRangeInserted(prevSize, movieList.getMovieList().size());
                                        isLoading = false;
                                    }
                                }, 2000);
                            }
                        });
                    }
                }
            }
        });
    }
}
