package com.abdullahalomair.flickerexplorer.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class Photo (
    val id : String,
    val owner : String,
    val secret : String,
    val server : Int,
    val farm : Int,
    val title : String,
    val ispublic : Int,
    val isfriend : Int,
    val isfamily : Int,
    @SerializedName("url_c") var url: String = "",
)