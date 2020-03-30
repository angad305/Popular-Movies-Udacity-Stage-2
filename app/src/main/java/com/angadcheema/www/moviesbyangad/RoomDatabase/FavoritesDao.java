/*
 * *
 *  * Created by Angad Cheema.
 *  * Github: @angad305
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 30/3/20 2:39 PM
 *
 */

package com.angadcheema.www.moviesbyangad.RoomDatabase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;

@Dao
public interface FavoritesDao {


  @Insert
  public void insert(FavoritesEntity favoriteTitle);


  @Query("DELETE FROM favorites_table")
  void deleteAll();

//  @Delete
//  public void deleteFavorite(FavoritesEntity favoritesEntity);

  @Query("DELETE FROM favorites_table WHERE favoriteTitle = :title")
      public void deleteByTitle(String title);

  @Query("SELECT * from favorites_table")
  public LiveData<List<FavoritesEntity>> getAllFavorites();


}
