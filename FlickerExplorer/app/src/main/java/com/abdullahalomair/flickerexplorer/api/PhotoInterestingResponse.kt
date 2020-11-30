package com.abdullahalomair.flickerexplorer.api

import com.abdullahalomair.flickerexplorer.model.GalleryItem
import com.google.gson.annotations.SerializedName

class PhotoInterestingResponse {
    @SerializedName("photo")
    lateinit var galleryItems: List<GalleryItem>
}