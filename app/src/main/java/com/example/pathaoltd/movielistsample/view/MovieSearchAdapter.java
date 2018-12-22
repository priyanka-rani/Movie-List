package com.example.pathaoltd.movielistsample.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.pathaoltd.movielistsample.R;
import com.example.pathaoltd.movielistsample.databinding.ItemMovieBinding;
import com.example.pathaoltd.movielistsample.databinding.ItemSearchBinding;
import com.example.pathaoltd.movielistsample.model.Movie;
import com.example.pathaoltd.movielistsample.network.ApiUtils;
import com.example.pathaoltd.movielistsample.util.MovieListFetchListener;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.ViewHolder>{

    private List<Movie> data;
    private MovieListFetchListener listener;
    private Context context;

    public MovieSearchAdapter(Context context, List<Movie> data) {
        this.context = context;
        this.data = data;
        this.listener = (MovieListFetchListener)context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_search, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemSearchBinding binding = holder.binding;
        Movie movie = data.get(position);
        binding.setData(movie);
        String url = ApiUtils.IMAGE_BASE_URL+movie.getBackdropPath();

        Glide.with(context)
                .load(url)
                .into(binding.ivThumb);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ItemSearchBinding binding;
        ViewHolder(ItemSearchBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
