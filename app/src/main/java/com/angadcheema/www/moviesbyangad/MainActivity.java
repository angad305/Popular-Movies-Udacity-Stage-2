/*
 * *
 *  * Created by Angad Cheema.
 *  * Github: @angad305
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 30/3/20 2:39 PM
 *
 */

package com.angadcheema.www.moviesbyangad;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.angadcheema.www.moviesbyangad.RoomDatabase.FavoritesEntity;
import com.angadcheema.www.moviesbyangad.RoomDatabase.FavoritesListAdapter;
import com.angadcheema.www.moviesbyangad.RoomDatabase.FavoritesViewModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements
    LoaderManager.LoaderCallbacks<String> {

  public static final int LOADER_ID_0 = 0;
  private final String CURRENT_SELECTION = "";
  public RecyclerViewAdapter adapter;
  private RecyclerView recyclerView;
  private GridLayoutManager gridLayoutManager;
  public String queryString;
  private ArrayList<Integer> movieIDs = new ArrayList<>();
  private ArrayList<String> movieTitles = new ArrayList<>();
  private ArrayList<String> mPosters = new ArrayList<>();
  private ArrayList<String> mOverview = new ArrayList<>();
  private ArrayList<String> mReleaseDate = new ArrayList<>();
  private ArrayList<Double> mVoteAverage = new ArrayList<>();
  private SharedPreferences mPreferences;
  private String sharedPrefFile = "com.angadcheema.www.moviesbyangad";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

    queryString = mPreferences.getString(CURRENT_SELECTION, "popular");

    if (isOnline()) {
      runLoader(queryString);
    } else {
      Toast.makeText(this, "Internet Not Connected", Toast.LENGTH_LONG).show();
    }


  }

  // Configuration CHange retaining recycler view state on landscape


  @Override
  protected void onPause() {
    super.onPause();
    SharedPreferences.Editor preferencesEditor = mPreferences.edit();
    preferencesEditor.putString(CURRENT_SELECTION, queryString);
    preferencesEditor.apply();

  }

  public void runLoader(String queryString) {

    Bundle bundle = new Bundle();
    bundle.putString("queryString", queryString);

    getSupportLoaderManager().restartLoader(LOADER_ID_0, bundle, this);
    if (getSupportLoaderManager().getLoader(LOADER_ID_0) != null) {
      getSupportLoaderManager().initLoader(LOADER_ID_0, null, this);

    }
  }

  public boolean isOnline() {
    Runtime runtime = Runtime.getRuntime();
    try {
      Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
      int exitValue = ipProcess.waitFor();
      return (exitValue == 0);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return false;
  }

  public void resetLoaderData() {
    movieIDs.clear();
    movieTitles.clear();
    mPosters.clear();
    mOverview.clear();
    mReleaseDate.clear();
    mVoteAverage.clear();
  }

  ////MENUS

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle item selection
    switch (item.getItemId()) {
      case R.id.top_rated:
        resetLoaderData();
        if (isOnline()) {
          queryString = "top_rated";
          runLoader(queryString);
          getSupportActionBar().setTitle("Top Rated");

        } else {
          Toast.makeText(this, "Internet Not Connected", Toast.LENGTH_LONG).show();
        }
        return true;

      case R.id.popular:
        resetLoaderData();
        if (isOnline()) {
          queryString = "popular";
          runLoader(queryString);
          getSupportActionBar().setTitle("Popular");
        } else {
          Toast.makeText(this, "Internet Not Connected", Toast.LENGTH_LONG).show();
        }
        return true;

      case R.id.favoritesMenu:

        getSupportActionBar().setTitle("Favorites");
        favoritesRecycler();

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void favoritesRecycler() {

    FavoritesViewModel favoritesViewModel;
    favoritesViewModel = ViewModelProviders.of(this).get(FavoritesViewModel.class);

    final FavoritesListAdapter favoritesListAdapter;
    recyclerView = findViewById(R.id.recyclerView);
    favoritesListAdapter = new FavoritesListAdapter(this);
    recyclerView.setAdapter(favoritesListAdapter);
    recyclerView.addItemDecoration(new DividerItemDecoration(this,
        DividerItemDecoration.HORIZONTAL));
    recyclerView.addItemDecoration(new DividerItemDecoration(this,
        DividerItemDecoration.VERTICAL));

    gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
    recyclerView.setLayoutManager(gridLayoutManager);

    favoritesViewModel.getAllFavorites().observe(this, new Observer<List<FavoritesEntity>>() {
      @Override
      public void onChanged(@Nullable final List<FavoritesEntity> favoritesEntities) {
        favoritesListAdapter.setfavorites(favoritesEntities);
      }
    });

  }

  ////LOADER METHODS.........

  @NonNull
  @Override
  public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {

    String queryString = "";
    if (bundle != null) {
      queryString = bundle.getString("queryString");
    }

    return new MoviesLoader(this, queryString);
  }

  @Override
  public void onLoadFinished(@NonNull Loader<String> loader, String s) {
    if (movieIDs.isEmpty()) {
      try {
        JSONObject main = new JSONObject(s);
        JSONArray arr = main.getJSONArray("results");
        for (int i = 0; i < arr.length(); i++) {
          String poster_base_url = "http://image.tmdb.org/t/p/w185";
          JSONObject movie = arr.getJSONObject(i);
          String poster = poster_base_url + movie.getString("poster_path");
          mPosters.add(poster);
          int id = movie.getInt("id");
          movieIDs.add(id);
          String title = movie.getString("title");
          movieTitles.add(title);
          String overview = movie.getString("overview");
          mOverview.add(overview);
          String releaseDate = movie.getString("release_date");
          mReleaseDate.add(releaseDate);
          Double voteAverage = movie.getDouble("vote_average");
          mVoteAverage.add(voteAverage);
          Log.d("ArrayPrint", "onLoadFinished: " + movieIDs + "  " + movieTitles);
          stopLoader(id);
        }

      } catch (JSONException e) {
        e.printStackTrace();
      }
      recyclerView = findViewById(R.id.recyclerView);
      adapter = new RecyclerViewAdapter(this, movieIDs, movieTitles, mPosters, mOverview,
          mReleaseDate, mVoteAverage);
      recyclerView.setAdapter(adapter);
      recyclerView.addItemDecoration(new DividerItemDecoration(this,
          DividerItemDecoration.HORIZONTAL));
      recyclerView.addItemDecoration(new DividerItemDecoration(this,
          DividerItemDecoration.VERTICAL));

      gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
      recyclerView.setLayoutManager(gridLayoutManager);

    }
  }

  @Override
  public void onLoaderReset(@NonNull Loader<String> loader) {

  }

  void stopLoader(int id) {
    getLoaderManager().destroyLoader(id);
  }
}



