package com.abdullahalomair.flickerexplorer.controller

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.abdullahalomair.flickerexplorer.R


class FlickrApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        FlickrRepository.initialize(this)
    }
}