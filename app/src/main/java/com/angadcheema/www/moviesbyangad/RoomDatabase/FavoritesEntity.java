/*
 * *
 *  * Created by Angad Cheema.
 *  * Github: @angad305
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 30/3/20 2:39 PM
 *
 */

package com.angadcheema.www.moviesbyangad.RoomDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.ArrayList;

@Entity(tableName = "favorites_table")
public class FavoritesEntity {

  //Favorite TITLE

  @PrimaryKey
  @NonNull
  @ColumnInfo(name = "favoriteTitle")
  private String favoriteTitle;

  //ID
  @NonNull
  @ColumnInfo(name = "favID")
  private int favID;

  //ImageURL
  @NonNull
  @ColumnInfo(name = "favImageUrl")
  private String favImageUrl;

  //Overview
  @NonNull
  @ColumnInfo(name = "favOverview")
  private String favOverview;

  //Date
  @NonNull
  @ColumnInfo(name = "favDate")
  private String favDate;

  //Votes
  @NonNull
  @ColumnInfo(name = "favVotes")
  private Double favVotes;

  //Reviews
  @ColumnInfo(name = "favReview")
  private String favReview;


  //Trailers1
  @ColumnInfo(name = "favTrailer1")
  private String favTrailer1;

  //Trailers2
  @ColumnInfo(name = "favTrailer2")
  private String favTrailer2;



  @NonNull
  public String getFavoriteTitle() {
    return favoriteTitle;
  }

  public void setFavoriteTitle(@NonNull String favoriteTitle) {
    this.favoriteTitle = favoriteTitle;
  }

  @NonNull
  public int getFavID() {
    return favID;
  }

  public void setFavID(@NonNull int favID) {
    this.favID = favID;
  }

  @NonNull
  public String getFavImageUrl() {
    return favImageUrl;
  }

  public void setFavImageUrl(@NonNull String favImageUrl) {
    this.favImageUrl = favImageUrl;
  }

  @NonNull
  public String getFavOverview() {
    return favOverview;
  }

  public void setFavOverview(@NonNull String favOverview) {
    this.favOverview = favOverview;
  }

  @NonNull
  public String getFavDate() {
    return favDate;
  }

  public void setFavDate(@NonNull String favDate) {
    this.favDate = favDate;
  }

  @NonNull
  public Double getFavVotes() {
    return favVotes;
  }

  public void setFavVotes(@NonNull Double favVotes) {
    this.favVotes = favVotes;
  }

  @NonNull
  public String getFavReview() {
    return favReview;
  }

  public void setFavReview(@NonNull String favReview) {
    this.favReview = favReview;
  }
  public String getFavTrailer1() {
    return favTrailer1;
  }

  public void setFavTrailer1(String favTrailer1) {
    this.favTrailer1 = favTrailer1;
  }

  public String getFavTrailer2() {
    return favTrailer2;
  }

  public void setFavTrailer2(String favTrailer2) {
    this.favTrailer2 = favTrailer2;
  }



  //Constructor

  public FavoritesEntity(@NonNull String favoriteTitle, @NonNull int favID,
      @NonNull String favImageUrl, @NonNull String favOverview, @NonNull String favDate,
      @NonNull Double favVotes, @NonNull String favReview, String favTrailer1, String favTrailer2 ) {
    this.favoriteTitle = favoriteTitle;
    this.favID = favID;
    this.favImageUrl = favImageUrl;
    this.favOverview = favOverview;
    this.favDate = favDate;
    this.favVotes = favVotes;
    this.favReview = favReview;
    this.favTrailer1 = favTrailer1;
    this.favTrailer2 = favTrailer2;
  }

  public FavoritesEntity() {

  }

}