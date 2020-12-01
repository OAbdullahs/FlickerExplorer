package com.abdullahalomair.flickerexplorer.api

import com.abdullahalomair.flickerexplorer.model.Comment
import com.google.gson.annotations.SerializedName

class CommentResponse {
    @SerializedName("comment")
    var comment: List<Comment> = emptyList()
}