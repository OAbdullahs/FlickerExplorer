package com.abdullahalomair.flickerexplorer.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abdullahalomair.flickerexplorer.controller.FlickrRepository
import com.abdullahalomair.flickerexplorer.model.Photo

class LocalPhotosFragmentViewModel: ViewModel() {

    private val getRepository = FlickrRepository.get()

    fun fetchLocalPhotos(lat:String,lon:String): LiveData<List<Photo>>
    = getRepository.getLocalPhotos(lat,lon)


}