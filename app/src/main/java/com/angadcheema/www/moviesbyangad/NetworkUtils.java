/*
 * *
 *  * Created by Angad Cheema.
 *  * Github: @angad305
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 30/3/20 2:39 PM
 *
 */

package com.angadcheema.www.moviesbyangad;

import android.net.Uri;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

  private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
  private static final String api_key = "api_key";
  private static String myAPI = "///ENTER YOUR API KEY HERE//";

  public static String getMoviesJson(String query) {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String moviesJson = null;

    try {

      Uri uri = Uri.parse(BASE_URL).buildUpon()
          .appendPath(query)
          .appendQueryParameter(api_key, myAPI)
          .build();
      URL requestURL = new URL(uri.toString());

      Log.d("URL", "URL JSON TO FETCH: " + requestURL);

      urlConnection = (HttpURLConnection) requestURL.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      InputStream inputStream = urlConnection.getInputStream();
      reader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder builder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
        builder.append("\n");
      }

      if (builder.length() == 0) {
        return null;
      }

      moviesJson = builder.toString();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {

      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (reader != null) {

        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    }

    Log.d("JSONSTRING", "getMoviesJson: " + moviesJson);
    return moviesJson;


  }

  ///GET REVIEWS////
  public static String getReviews(String movieID) {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String reviewJson = null;

    try {

      Uri uri = Uri.parse(BASE_URL).buildUpon()
          .appendPath(movieID)
          .appendPath("reviews")
          .appendQueryParameter(api_key, myAPI)
          .build();
      URL requestURL = new URL(uri.toString());

      Log.d("Review", "REVIEW URL JSON TO FETCH: " + requestURL);

      urlConnection = (HttpURLConnection) requestURL.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      InputStream inputStream = urlConnection.getInputStream();
      reader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder builder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
        builder.append("\n");
      }

      if (builder.length() == 0) {
        return null;
      }

      reviewJson = builder.toString();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {

      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (reader != null) {

        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    }

    Log.d("JSONSTRING", "getMoviesJson: " + reviewJson);
    return reviewJson;


  }

  ///GET Trailers////

  public static String getTrailers(String movieID) {

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String trailersJson = null;

    try {

      //https://api.themoviedb.org/3/movie/344334/videos?api_key=21c2888bc0205ebded3be0f790896966

      Uri uri = Uri.parse(BASE_URL).buildUpon()
          .appendPath(movieID)
          .appendPath("videos")
          .appendQueryParameter(api_key, myAPI)
          .build();
      URL requestURL = new URL(uri.toString());

      Log.d("Trailers", "Trailes to Fetch: " + requestURL);

      urlConnection = (HttpURLConnection) requestURL.openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.connect();

      InputStream inputStream = urlConnection.getInputStream();
      reader = new BufferedReader(new InputStreamReader(inputStream));
      StringBuilder builder = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        builder.append(line);
        builder.append("\n");
      }

      if (builder.length() == 0) {
        return null;
      }

      trailersJson = builder.toString();

    } catch (IOException e) {
      e.printStackTrace();
    } finally {

      if (urlConnection != null) {
        urlConnection.disconnect();
      }
      if (reader != null) {

        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
    }

    Log.d("JSONSTRING", "getMoviesJson: " + trailersJson);
    return trailersJson;


  }


}