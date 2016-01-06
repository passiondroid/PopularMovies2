package com.app.popularmovies.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by Arif on 02-Jan-16.
 */
public class PopularmoviesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
