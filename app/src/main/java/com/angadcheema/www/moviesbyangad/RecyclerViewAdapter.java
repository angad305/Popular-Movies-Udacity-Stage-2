/*
 * *
 *  * Created by Angad Cheema.
 *  * Github: @angad305
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 30/3/20 2:39 PM
 *
 */

package com.angadcheema.www.moviesbyangad;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.angadcheema.www.moviesbyangad.RoomDatabase.FavoritesEntity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


  Context ctx;
  ArrayList<Integer> ids;
  ArrayList<String> title;
  ArrayList<String> posters;
  ArrayList<String> Overview;
  ArrayList<String> ReleaseDate;
  ArrayList<Double> VoteAverage;


  public RecyclerViewAdapter(Context ctx, ArrayList<Integer> ids,
      ArrayList<String> title, ArrayList<String> posters,
      ArrayList<String> overview, ArrayList<String> releaseDate,
      ArrayList<Double> voteAverage) {
    this.ctx = ctx;
    this.ids = ids;
    this.title = title;
    this.posters = posters;
    Overview = overview;
    ReleaseDate = releaseDate;
    VoteAverage = voteAverage;
  }

  @NonNull
  @Override
  public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.movies, viewGroup, false);
    ViewHolder holder = new ViewHolder(view, this);
    return holder;
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder,
      final int position) {
    Log.d("ON_BINDER", "onBindViewHolder: ");

    viewHolder.mMovieName.setText(title.get(position));
    //POSTERS
    Glide.with(ctx)
        .asBitmap()
        .load(posters.get(position))
        .into(viewHolder.image);

    viewHolder.movieslayout.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        Toast.makeText(ctx, title.get(position), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(ctx, DetailsPane.class);
        intent.putExtra("title", title.get(position));
        intent.putExtra("id", ids.get(position));
        intent.putExtra("image_url", posters.get(position));
        intent.putExtra("overview", Overview.get(position));
        intent.putExtra("releaseDate", ReleaseDate.get(position));
        intent.putExtra("votes", VoteAverage.get(position));
        ctx.startActivity(intent);

      }
    });

  }

  @Override
  public int getItemCount() {
    return ids.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    ImageView image;
    TextView mMovieName;
    RecyclerViewAdapter mRecyclerViewAdapter;
    RelativeLayout movieslayout;

    public ViewHolder(@NonNull View itemView, RecyclerViewAdapter adapter) {
      super(itemView);
      image = itemView.findViewById(R.id.image);
      mMovieName = itemView.findViewById(R.id.movieName);
      this.mRecyclerViewAdapter = adapter;
      movieslayout = itemView.findViewById(R.id.moviesLayoutHolder);
    }
  }

}
