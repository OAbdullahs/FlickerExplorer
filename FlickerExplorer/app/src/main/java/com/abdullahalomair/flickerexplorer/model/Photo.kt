package com.abdullahalomair.flickerexplorer.model

import android.net.Uri

data class Photo (
    val id : String,
    val owner : String,
    val secret : String,
    val server : Int,
    val farm : Int,
    val title : String,
    val ispublic : Int,
    val isfriend : Int,
    val isfamily : Int
){
    val photoLocalPageUri: Uri
        get() {
            return Uri.parse("https://www.flickr.com/photos/")
                .buildUpon()
                .appendPath(owner)
                .appendPath(id)
                .build()

        }
}