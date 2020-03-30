/*
 * *
 *  * Created by Angad Cheema.
 *  * Github: @angad305
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 30/3/20 2:39 PM
 *
 */

package com.angadcheema.www.moviesbyangad;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.angadcheema.www.moviesbyangad.RoomDatabase.FavoritesEntity;
import com.angadcheema.www.moviesbyangad.RoomDatabase.FavoritesListAdapter;
import com.angadcheema.www.moviesbyangad.RoomDatabase.FavoritesViewModel;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsPane extends AppCompatActivity implements
    LoaderManager.LoaderCallbacks<String> {

  private static int id = 0;
  private String title = null;
  private String imageUrl = null;
  private String overview = null;
  private String releasedate = null;
  private double votes = 0.0;

  TextView reviewDetails;
  private String movieReview;
  private String idToPass;
  Bundle bundle2 = new Bundle();

  private ArrayList<String> mtrailers = new ArrayList<>();
  private String myTrailer1 = " ";
  private String myTrailer2 = " ";

  ToggleButton toggleButton;
  private FavoritesViewModel favoritesViewModel;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detailspane);

    reviewDetails = (TextView) findViewById(R.id.reviews);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      title = bundle.getString("title");
      id = bundle.getInt("id");
      imageUrl = bundle.getString("image_url");
      overview = bundle.getString("overview");
      releasedate = bundle.getString("releaseDate");
      votes = bundle.getDouble("votes", 0.0);
      idToPass = String.valueOf(id);
      Log.d("IDtopass", "Details Pane id: " + idToPass);
    }

    TextView detailsTitle = (TextView) findViewById(R.id.details_title);
    detailsTitle.setText(title);

    TextView idPane = (TextView) findViewById(R.id.id);
    idPane.setText(String.valueOf(id));

    ImageView detailsPoster = findViewById(R.id.details_image);
    Glide.with(this)
        .asBitmap()
        .load(imageUrl)
        .into(detailsPoster);

    TextView detailSynopsis = (TextView) findViewById(R.id.details_overview);
    detailSynopsis.setText(overview);

    TextView detailsReleaseDate = (TextView) findViewById(R.id.details_date);
    detailsReleaseDate.setText(releasedate);

    TextView detailsVotes = (TextView) findViewById(R.id.details_rating);
    detailsVotes.setText(Double.toString(votes));

    runReviewLoader();
    runTrailersLoader();

    toggleButton = findViewById(R.id.button_favorite);

    SharedPreferences sharedPrefs = getSharedPreferences("favoritesSharedPref", MODE_PRIVATE);
    toggleButton.setChecked(sharedPrefs.getBoolean("Favorited" + id, false));

    toggleButton.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        boolean on = toggleButton.isChecked();

        favoritesViewModel = ViewModelProviders.of(DetailsPane.this)
            .get(FavoritesViewModel.class);
        FavoritesEntity favTitle = new FavoritesEntity(title, id, imageUrl, overview, releasedate,
            votes, movieReview, myTrailer1, myTrailer2);
        if (on) {

          SharedPreferences.Editor editor = getSharedPreferences("favoritesSharedPref",
              MODE_PRIVATE).edit();
          editor.putBoolean("Favorited" + id, true);
          editor.commit();

          Toast.makeText(DetailsPane.this, "Movie Favorited", Toast.LENGTH_SHORT).show();
          favoritesViewModel.insert(favTitle);

        } else {

          SharedPreferences.Editor editor = getSharedPreferences("favoritesSharedPref",
              MODE_PRIVATE).edit();
          editor.putBoolean("Favorited" + id, false);
          editor.commit();

          Toast.makeText(DetailsPane.this, "Movie UN - Favorited", Toast.LENGTH_SHORT).show();

          favoritesViewModel.deleteSingleFavoriteAsync(title);


        }


      }
    });
  }


  public void runReviewLoader() {

    bundle2.putString("movieID", idToPass);

    getSupportLoaderManager().restartLoader(2, bundle2, this);

    if (getSupportLoaderManager().getLoader(2) != null) {
      getSupportLoaderManager().initLoader(2, null, this);
    }

  }

  public void runTrailersLoader() {

    getSupportLoaderManager().restartLoader(3, bundle2, this);

    if (getSupportLoaderManager().getLoader(3) != null) {
      getSupportLoaderManager().initLoader(3, null, this);
    }

  }

  public void trailer1(View view) {
    if (myTrailer1 == " ") {

      Toast.makeText(DetailsPane.this, "Sorry No Trailers", Toast.LENGTH_SHORT).show();

    } else {
      Intent youtubeVideo = new Intent(Intent.ACTION_VIEW);
      youtubeVideo.setData(Uri.parse(myTrailer1));
      startActivity(youtubeVideo);
    }

  }


  public void trailer2(View view) {
    if (myTrailer2 == " ") {
      Toast.makeText(DetailsPane.this, "Sorry No more Trailers", Toast.LENGTH_SHORT).show();

    } else {

      Intent youtubeVideo = new Intent(Intent.ACTION_VIEW);
      youtubeVideo.setData(Uri.parse(myTrailer2));
      startActivity(youtubeVideo);
    }
  }


  @NonNull
  @Override
  public Loader<String> onCreateLoader(int id, @Nullable Bundle bundle2) {
    if (id == 2) {
      String mReviewString = "";
      if (bundle2 != null) {
        mReviewString = bundle2.getString("movieID");
        Log.d("BUNDLE_STRING", "MY_REVIEWS_BUNDLE_ID: " + mReviewString);
      }
      return new ReviewsLoader(this, mReviewString);
    } else if (id == 3) {
      String mTrailerString = "";
      if (bundle2 != null) {
        mTrailerString = bundle2.getString("movieID");
        Log.d("BUNDLE_STRING", "MY_TRAILERS_BUNDLE_ID: " + mTrailerString);
      }

      return new TrailersLoader(this, mTrailerString);

    }

    return null;
  }


  @Override
  public void onLoadFinished(@NonNull Loader<String> loader, String s) {
    int id = loader.getId();
    if (id == 2) {

      try {
        StringBuilder builder = new StringBuilder();
        JSONObject main = new JSONObject(s);
        JSONArray arr = main.getJSONArray("results");

        //SHows last 3 Reviews
        int noOfReviews = 0;
        if (arr.length() < 3) {
          noOfReviews = arr.length();
        } else {
          noOfReviews = 3;
        }

        for (int i = 0; i < noOfReviews; i++) {

          JSONObject reveiws = arr.getJSONObject(i);

          String review_author = reveiws.getString("author");

          String review = reveiws.getString("content");

          builder.append("Author: ");
          builder.append(review_author);
          builder.append("\n\n");
          builder.append("Review: ");
          builder.append(review);
          builder.append("\n\n--------\n\n");

          movieReview = String.valueOf(builder);
          if (!movieReview.isEmpty()) {

            reviewDetails.setText(movieReview);
          }
        }

      } catch (JSONException e) {
        e.printStackTrace();
      }
    } else if (id == 3) {
      try {

        JSONObject main = new JSONObject(s);
        JSONArray arr = main.getJSONArray("results");

        for (int i = 0; i < arr.length(); i++) {

          JSONObject trailersArr = arr.getJSONObject(i);
          String youtube_base = "https://www.youtube.com/watch?v=";
          String trailer = youtube_base + trailersArr.getString("key");
          mtrailers.add(trailer);

        }

        int i = mtrailers.size();
        Log.d("SIZE", "TRAILERSIZE: "+ i);

        if (!mtrailers.isEmpty()) {
          myTrailer1 = String.valueOf(mtrailers.get(0));
        }
        if (i > 1) {
          myTrailer2 = String.valueOf(mtrailers.get(1));
        }
        String trailerList = "";
        for (String mt : mtrailers) {
          trailerList = trailerList + mt + "\n";
        }

        Log.d("TRAILERS", "TRAILERS onLoadFinished: " + trailerList + "\n" + mtrailers);

      } catch (JSONException e) {
        e.printStackTrace();


      }

    }
  }

  @Override
  public void onLoaderReset(@NonNull Loader<String> loader) {

  }
}