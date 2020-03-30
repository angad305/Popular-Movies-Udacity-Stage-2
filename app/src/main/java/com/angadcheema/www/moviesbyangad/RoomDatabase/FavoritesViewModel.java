/*
 * *
 *  * Created by Angad Cheema.
 *  * Github: @angad305
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 30/3/20 2:39 PM
 *
 */

package com.angadcheema.www.moviesbyangad.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {

  private FavoritesRepository mRepository;


  private LiveData<List<FavoritesEntity>> mAllFavorites;


  public FavoritesViewModel(@NonNull Application application) {
    super(application);
    mRepository = new FavoritesRepository(application);
    mAllFavorites = mRepository.getAllFavorites();

  }

  public LiveData<List<FavoritesEntity>> getAllFavorites() {
    return mAllFavorites;
  }

  public void insert(FavoritesEntity favoritesEntity) {
    mRepository.insert(favoritesEntity);
  }

  public void deleteAll() {
    mRepository.deleteAll();
  }


  public void deleteSingleFavoriteAsync(String favTitle) {
    mRepository.deleteFavorite(favTitle);
  }


}