package com.abdullahalomair.flickerexplorer.model

import com.google.gson.annotations.SerializedName

data class Comments (
    @SerializedName("photo_id") val photo_id : Int,
    @SerializedName("comment") val comment : List<Comment>
        )