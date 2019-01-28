package com.pri.movielistsample.view;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pri.movielistsample.R;
import com.pri.movielistsample.databinding.ItemSearchBinding;
import com.pri.movielistsample.model.Movie;
import com.pri.movielistsample.network.ApiUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.ViewHolder> {

    private List<Movie> data;

    public MovieSearchAdapter(List<Movie> data) {
        this.data = data;
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
        String url = ApiUtils.IMAGE_BASE_URL + movie.getBackdropPath();
        Picasso.get().load(url).into(binding.ivThumb);
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
