/*
 * *
 *  * Created by Angad Cheema.
 *  * Github: @angad305
 *  * Copyright (c) 2020 . All rights reserved.
 *  * Last modified 30/3/20 2:39 PM
 *
 */

package com.angadcheema.www.moviesbyangad.RoomDatabase;

import static android.support.constraint.Constraints.TAG;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

@Database(entities = {FavoritesEntity.class}, version = 1)
public abstract class FavoritesDatabase extends RoomDatabase {

  private static volatile FavoritesDatabase INSTANCE;
  private static RoomDatabase.Callback roomDatabaseCallBack =
      new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
          super.onOpen(db);
          new PopulateDbAsync(INSTANCE).execute();
        }
      };

  static FavoritesDatabase getDatabase(final Context context) {
    if (INSTANCE == null) {
      synchronized (FavoritesDatabase.class) {
        if (INSTANCE == null) {
          //database creation
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
              FavoritesDatabase.class, "favorites_database")
              .addCallback(roomDatabaseCallBack)
              .build();

        }
      }
    }
    return INSTANCE;


  }

  public abstract FavoritesDao favoritesDao();  //Method to create a DAO instance

  private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

    private final FavoritesDao mDao;

    PopulateDbAsync(FavoritesDatabase db) {
      mDao = db.favoritesDao();

    }

    @Override
    protected Void doInBackground(final Void... params) {
      //mDao.deleteAll();
      return null;
    }
  }
}