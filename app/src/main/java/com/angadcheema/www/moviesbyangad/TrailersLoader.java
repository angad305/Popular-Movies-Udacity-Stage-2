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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class TrailersLoader extends AsyncTaskLoader {

  String mTrailerString;

  //Constructor
  public TrailersLoader(@NonNull Context context, String TrailerString) {
    super(context);
    this.mTrailerString = TrailerString;
  }

  //FIRST THING LOADS WHEN LOADER IS CALLED
  @Override
  protected void onStartLoading() {
    super.onStartLoading();
    forceLoad();
  }


  @Nullable
  @Override
  public Object loadInBackground() {
    return NetworkUtils.getTrailers(mTrailerString);
  }
}
