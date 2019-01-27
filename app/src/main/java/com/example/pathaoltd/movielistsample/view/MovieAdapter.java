package com.example.pathaoltd.movielistsample.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.pathaoltd.movielistsample.R;
import com.example.pathaoltd.movielistsample.databinding.ItemMovieBinding;
import com.example.pathaoltd.movielistsample.model.Movie;
import com.example.pathaoltd.movielistsample.network.ApiUtils;
import com.example.pathaoltd.movielistsample.util.MovieListFetchListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private List<Movie> data;
    private MovieListFetchListener listener;
    private Context context;

    public MovieAdapter(Context context, List<Movie> data) {
        this.context = context;
        this.data = data;
        this.listener = (MovieListFetchListener)context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemMovieBinding binding = holder.binding;
        Movie movie = data.get(position);
        binding.setData(movie);
        String url = ApiUtils.IMAGE_BASE_URL+movie.getBackdropPath();

        Picasso.get().load(url)
                .into(binding.ivThumb);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;
        ViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
