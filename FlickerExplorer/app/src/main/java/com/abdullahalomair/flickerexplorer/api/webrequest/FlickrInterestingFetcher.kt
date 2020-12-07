package com.abdullahalomair.flickerexplorer.api.webrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdullahalomair.flickerexplorer.api.FlickrApi
import com.abdullahalomair.flickerexplorer.api.FlickrInterestingResponse
import com.abdullahalomair.flickerexplorer.api.PhotoInterestingResponse
import com.abdullahalomair.flickerexplorer.api.exception.WrongUrlException
import com.abdullahalomair.flickerexplorer.model.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FlickrInterestingFetcher {
    private val flickrApi: FlickrApi

    init {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }
    private fun fetchPhotosRequest(): Call<FlickrInterestingResponse> {
        return flickrApi.fetchInterestingPhotos()
    }
    fun fetchInterestingPhotos(): LiveData<List<Photo>> {
        return fetchPhotoMetadata(fetchPhotosRequest())
    }
    private fun fetchPhotoMetadata(flickrRequest: Call<FlickrInterestingResponse>)
            : LiveData<List<Photo>> {
        val responseLiveData: MutableLiveData<List<Photo>> = MutableLiveData()

        flickrRequest.enqueue(object : Callback<FlickrInterestingResponse> {

            override fun onFailure(call: Call<FlickrInterestingResponse>, t: Throwable) {
               if (t == WrongUrlException()){
                   //TODO
               }
            }

            override fun onResponse(call: Call<FlickrInterestingResponse>, response: Response<FlickrInterestingResponse>) {
                val flickrResponse: FlickrInterestingResponse? = response.body()
                val photoResponse: PhotoInterestingResponse? = flickrResponse?.photos
                var galleryItems: List<Photo> = photoResponse?.galleryItems
                    ?: mutableListOf()
                galleryItems = galleryItems.filterNot {
                    it.url.isBlank()
                }
                responseLiveData.value = galleryItems
            }
        })

        return responseLiveData
    }
}