package com.abdullahalomair.flickerexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abdullahalomair.flickerexplorer.controller.FlickrRepository
import com.abdullahalomair.flickerexplorer.database.LikedPhotoDB

class LikedPhotoFragmentViewModel: ViewModel() {

    private val getRepository = FlickrRepository.get()

    fun getPhotos(): LiveData<List<LikedPhotoDB>>
    = getRepository.getLikedPhotos()

}