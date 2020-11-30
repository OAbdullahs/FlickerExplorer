package com.abdullahalomair.flickerexplorer.controller

import android.app.Application


class FlickrApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FlickrRepository.initialize(this)
    }
}