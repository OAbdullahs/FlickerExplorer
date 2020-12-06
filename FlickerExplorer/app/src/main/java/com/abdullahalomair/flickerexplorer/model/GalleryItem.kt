package com.abdullahalomair.flickerexplorer.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class GalleryItem(
    var title: String = "",
    var id: String = "",
    @SerializedName("geo_is_public") var geoIsPublic:Int = 0,
    var latitude:String = "",
    var longitude:String = "",
    @SerializedName("url_c") var url: String = "",
    @SerializedName("owner") var owner: String = ""
)