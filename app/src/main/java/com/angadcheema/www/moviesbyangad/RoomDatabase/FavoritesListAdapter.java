/*
 * *
 *  * Created by Angad Cheema.
 *  * Github: @angad305
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 30/3/20 2:39 PM
 *
 */

package com.angadcheema.www.moviesbyangad.RoomDatabase;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.angadcheema.www.moviesbyangad.DetailsPane;
import com.angadcheema.www.moviesbyangad.R;
import com.angadcheema.www.moviesbyangad.RecyclerViewAdapter;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class FavoritesListAdapter extends
    RecyclerView.Adapter<FavoritesListAdapter.FavoritesViewHolder> {


  private LayoutInflater mInflater;
  private List<FavoritesEntity> mFavorites;
  Context context;

  public FavoritesListAdapter(Context context) {
    this.context = context;
    this.mInflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,
      int i) {
    View view = mInflater.inflate(R.layout.movies, viewGroup, false);
    return new FavoritesViewHolder(view, this);
  }

  @Override
  public void onBindViewHolder(
      @NonNull FavoritesViewHolder holder, final int position) {

    final FavoritesEntity current = mFavorites.get(position);

    if (mFavorites != null) {
      holder.mMovieName.setText(current.getFavoriteTitle());
    } else {
      // Covers the case of data not being ready yet.
      holder.mMovieName.setText("No Favorites Available");
    }

    Glide.with(context)
        .asBitmap()
        .load(current.getFavImageUrl())
        .into(holder.image);

    holder.movieslayout.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        Toast.makeText(context, current.getFavoriteTitle(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(context, DetailsPane.class);
        intent.putExtra("title", current.getFavoriteTitle());
        intent.putExtra("id", current.getFavID());
        intent.putExtra("image_url", current.getFavImageUrl());
        intent.putExtra("overview", current.getFavOverview());
        intent.putExtra("releaseDate", current.getFavDate());
        intent.putExtra("votes", current.getFavVotes());
        context.startActivity(intent);

      }
    });


  }

  public void setfavorites(List<FavoritesEntity> favoritesEntities) {
    this.mFavorites = favoritesEntities;
    notifyDataSetChanged();
  }

  @Override
  public int getItemCount() {
    if (mFavorites != null) {
      return mFavorites.size();
    } else {
      return 0;
    }
  }

  class FavoritesViewHolder extends ViewHolder {

    ImageView image;
    TextView mMovieName;
    FavoritesListAdapter mRecyclerViewAdapter;
    RelativeLayout movieslayout;

    private FavoritesViewHolder(@NonNull View itemView, FavoritesListAdapter adapter) {
      super(itemView);
      image = itemView.findViewById(R.id.image);
      mMovieName = itemView.findViewById(R.id.movieName);
      this.mRecyclerViewAdapter = adapter;
      movieslayout = itemView.findViewById(R.id.moviesLayoutHolder);
    }
  }
}