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
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import java.util.List;

public class FavoritesRepository {

  private FavoritesDao mFavoritesDao;
  private LiveData<List<FavoritesEntity>> mAllFavorites;


  public FavoritesRepository(Application application) {
    FavoritesDatabase db = FavoritesDatabase.getDatabase((application));
    mFavoritesDao = db.favoritesDao();
    mAllFavorites = mFavoritesDao.getAllFavorites();
  }

  public LiveData<List<FavoritesEntity>> getAllFavorites() {
    return mAllFavorites;
  }

  public void insert(FavoritesEntity favoritesEntity) {
    new insertAsyncTask(mFavoritesDao).execute(favoritesEntity);
  }

  public void deleteAll() {
    new deleteAsync(mFavoritesDao).execute();
  }

  public void deleteFavorite(String favTitle) {
    new deleteSingleFavoriteAsync(mFavoritesDao).execute(favTitle);
  }


  private class insertAsyncTask extends AsyncTask<FavoritesEntity, Void, Void> {

    private FavoritesDao mAsyncTaskDao;

    insertAsyncTask(FavoritesDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final FavoritesEntity... params) {
      mAsyncTaskDao.insert(params[0]);
      return null;
    }

  }

  private class deleteAsync extends AsyncTask<Void, Void, Void> {

    private FavoritesDao mAsyncTaskDao;

    deleteAsync(FavoritesDao dao) {
      mAsyncTaskDao = dao;
    }


    @Override
    protected Void doInBackground(Void... voids) {
      mAsyncTaskDao.deleteAll();
      return null;
    }
  }


  private class deleteSingleFavoriteAsync extends AsyncTask<String, Void, Void> {

    private FavoritesDao mAsyncTaskDao;

    deleteSingleFavoriteAsync(FavoritesDao dao) {
      mAsyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(String... strings) {
      mAsyncTaskDao.deleteByTitle(strings[0]);
      return null;
    }
  }

}