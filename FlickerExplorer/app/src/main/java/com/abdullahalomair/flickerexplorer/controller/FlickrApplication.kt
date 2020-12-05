package com.abdullahalomair.flickerexplorer.controller

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.abdullahalomair.flickerexplorer.R


class FlickrApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        FlickrRepository.initialize(this)
    }
}