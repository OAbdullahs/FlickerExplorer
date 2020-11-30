package com.abdullahalomair.flickerexplorer.api


import com.abdullahalomair.flickerexplorer.model.Photo
import com.google.gson.annotations.SerializedName

class PhotoLocalResponse {
    @SerializedName("photo")
    lateinit var photoGalleryItem: List<Photo>
}