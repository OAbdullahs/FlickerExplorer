package com.abdullahalomair.flickerexplorer.api

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {

    @GET("services/rest?method=flickr.interestingness.getList")
    fun fetchInterestingPhotos(): Call<FlickrInterestingResponse>

    @GET("services/rest/?method=flickr.photos.search")
    fun fetchLocalPhotos(): Call<FlickrLocalResponse>

    @GET("services/rest/?method=flickr.photos.comments.getList")
    fun fetchPhotoComments(): Call<FlickrCommentsResponse>
}