package com.abdullahalomair.flickerexplorer.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class Photo (
    val id : String = "",
    val title : String = "",
    var latitude:String = "",
    var longitude:String = "",
    @SerializedName("url_c") var url: String = "",
)