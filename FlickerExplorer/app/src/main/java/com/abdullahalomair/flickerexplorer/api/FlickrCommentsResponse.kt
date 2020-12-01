package com.abdullahalomair.flickerexplorer.api

import com.abdullahalomair.flickerexplorer.model.Comments
import com.google.gson.annotations.SerializedName


data class FlickrCommentsResponse (
     @SerializedName("comments") val comments : Comments,
     @SerializedName("stat") val stat : String
)
