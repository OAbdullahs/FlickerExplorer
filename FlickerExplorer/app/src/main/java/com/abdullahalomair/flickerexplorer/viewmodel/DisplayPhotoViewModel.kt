package com.abdullahalomair.flickerexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abdullahalomair.flickerexplorer.controller.FlickrRepository
import com.abdullahalomair.flickerexplorer.model.Comment


class DisplayPhotoViewModel: ViewModel() {

    private val getRepository = FlickrRepository.get()

    fun getPhotoComments(photoId:String): LiveData<List<Comment>> {
        return getRepository.getPhotoComments(photoId)
    }
}