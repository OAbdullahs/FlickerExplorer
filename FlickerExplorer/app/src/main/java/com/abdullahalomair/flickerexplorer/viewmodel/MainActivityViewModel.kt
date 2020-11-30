package com.abdullahalomair.flickerexplorer.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abdullahalomair.flickerexplorer.FlickrRepository
import com.abdullahalomair.flickerexplorer.model.Photo

class MainActivityViewModel: ViewModel() {

    private val repo = FlickrRepository.get()

    fun fetchLocalPhotos(lat:String,lon:String):LiveData<List<Photo>>{
        return repo.getLocalPhotos(lat,lon)
    }


}