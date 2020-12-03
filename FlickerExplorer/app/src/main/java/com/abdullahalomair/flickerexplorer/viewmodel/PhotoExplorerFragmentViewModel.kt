package com.abdullahalomair.flickerexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abdullahalomair.flickerexplorer.controller.FlickrRepository
import com.abdullahalomair.flickerexplorer.model.GalleryItem
import com.abdullahalomair.flickerexplorer.model.Photo

class PhotoExplorerFragmentViewModel: ViewModel() {

    private val getRepository = FlickrRepository.get()

    fun getInterestingPhotos():LiveData<List<GalleryItem>> {
        return getRepository.getInterestingPhotos()
    }
    fun getSearchedBasedPhotos(lan:String,long:String): LiveData<List<Photo>> {
        return getRepository.getLocalPhotos(lan,long)
    }
}