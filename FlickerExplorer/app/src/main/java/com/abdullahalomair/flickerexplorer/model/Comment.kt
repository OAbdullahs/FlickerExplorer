package com.abdullahalomair.flickerexplorer.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id : String,
    @SerializedName("author") val author : String,
    @SerializedName("author_is_deleted") val author_is_deleted : Int,
    @SerializedName("authorname") val authorname : String,
    @SerializedName("iconserver") val iconserver : Int,
    @SerializedName("iconfarm") val iconfarm : Int,
    @SerializedName("datecreate") val datecreate : Int,
    @SerializedName("permalink") val permalink : String,
    @SerializedName("path_alias") val path_alias : String,
    @SerializedName("realname") val realname : String,
    @SerializedName("_content") val _content : String
)