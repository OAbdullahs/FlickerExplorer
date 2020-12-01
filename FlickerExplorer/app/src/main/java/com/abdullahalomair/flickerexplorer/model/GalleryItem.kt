package com.abdullahalomair.flickerexplorer.model

import android.net.Uri
import com.google.gson.annotations.SerializedName

data class GalleryItem(
    var title: String = "",
    var id: String = "",
    @SerializedName("url_c") var url: String = "",
    @SerializedName("owner") var owner: String = ""
)