package com.abdullahalomair.flickerexplorer.api.webrequest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdullahalomair.flickerexplorer.api.FlickrApi
import com.abdullahalomair.flickerexplorer.api.FlickrLocalResponse
import com.abdullahalomair.flickerexplorer.api.PhotoLocalResponse
import com.abdullahalomair.flickerexplorer.api.exception.WrongLocationException
import com.abdullahalomair.flickerexplorer.model.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class FlickrLocalFetcher {

    private val flickrApi: FlickrApi

    init {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }
    private fun fetchPhotosRequest(lat:String,lon:String): Call<FlickrLocalResponse> {
        return flickrApi.fetchLocalPhotos(lat,lon)
    }
    open fun fetchLocalPhotos(lat:String,lon:String): LiveData<List<Photo>> {
        return fetchPhotoMetadata(fetchPhotosRequest(lat, lon))
    }

    private fun fetchPhotoMetadata(flickrRequest: Call<FlickrLocalResponse>)
    :LiveData<List<Photo>>{
        val responseLiveData: MutableLiveData<List<Photo>> = MutableLiveData()

        flickrRequest.enqueue(object: Callback<FlickrLocalResponse>{
            override fun onResponse(
                call: Call<FlickrLocalResponse>,
                response: Response<FlickrLocalResponse>
            ) {
                val flickrResponse: FlickrLocalResponse? = response.body()
                val photoResponse: PhotoLocalResponse? = flickrResponse?.photos
                var photos: List<Photo> = photoResponse?.photoGalleryItem
                    ?: mutableListOf()
                responseLiveData.value = photos
            }

            override fun onFailure(call: Call<FlickrLocalResponse>, t: Throwable) {
                if (t == WrongLocationException()){
                    //TODO PRINT TO USER AND PRINT INTERESTING INSTEAD
                }
            }

        })
        return responseLiveData
    }

}