package com.abdullahalomair.flickerexplorer.controller

import android.content.Context
import androidx.lifecycle.LiveData
import com.abdullahalomair.flickerexplorer.api.webrequest.FlickrLocalFetcher
import com.abdullahalomair.flickerexplorer.model.Photo

class FlickrRepository private constructor(context: Context){

    fun getLocalPhotos(lat:String, lon:String):LiveData<List<Photo>>{
        return FlickrLocalFetcher(lat,lon).fetchLocalPhotos()
    }

    companion object {
        private var INSTANCE: FlickrRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FlickrRepository(context)
            }
        }
        fun get(): FlickrRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}