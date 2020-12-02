package com.abdullahalomair.flickerexplorer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.abdullahalomair.flickerexplorer.controller.FlickrRepository
import com.abdullahalomair.flickerexplorer.database.LikedPhotoDB
import com.abdullahalomair.flickerexplorer.model.Comment


class DisplayPhotoViewModel: ViewModel() {

    private val getRepository = FlickrRepository.get()

    fun getPhotoComments(photoId:String): LiveData<List<Comment>> {
        return getRepository.getPhotoComments(photoId)
    }
    fun addLikedPhoto(likedPhotoDB: LikedPhotoDB)
            = getRepository.addPhotoToDB(likedPhotoDB)
    fun deleteLikedPhoto(likedPhotoDB: LikedPhotoDB)
            = getRepository.deletePhotoFromDB(likedPhotoDB)
    fun getLikedPhoto(id:String): LiveData<LikedPhotoDB?>
            = getRepository.getLikedPhoto(id)
    fun getLikedPhotos(): LiveData<List<LikedPhotoDB>>
            = getRepository.getLikedPhotos()

}