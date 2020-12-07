package com.abdullahalomair.flickerexplorer.api.webrequest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdullahalomair.flickerexplorer.api.*
import com.abdullahalomair.flickerexplorer.api.exception.WrongUrlException

import com.abdullahalomair.flickerexplorer.model.Comment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "FlickrCommentsFetcher"
class FlickrCommentsFetcher {
    private val flickrApi: FlickrApi

    init {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }
    private fun fetchPhotosRequest(photoId:String): Call<FlickrCommentsResponse> {
        return flickrApi.fetchPhotoComments(photoId)
    }
    fun fetchPhotoComments(photoId: String): LiveData<List<Comment>> {
        return fetchPhotoMetadata(fetchPhotosRequest(photoId))
    }
    private fun fetchPhotoMetadata(flickrRequest: Call<FlickrCommentsResponse>)
            : LiveData<List<Comment>> {
        val responseLiveData: MutableLiveData<List<Comment>> = MutableLiveData()

        flickrRequest.enqueue(object : Callback<FlickrCommentsResponse> {

            override fun onFailure(call: Call<FlickrCommentsResponse>, t: Throwable) {
                if (t == WrongUrlException()){
                    //TODO
                }
            }

            override fun onResponse(call: Call<FlickrCommentsResponse>, response: Response<FlickrCommentsResponse>) {

                val flickrResponse: FlickrCommentsResponse? = response.body()
                val photoResponse: CommentResponse? = flickrResponse?.comments
                val commentList: List<Comment> = photoResponse?.comment
                    ?: mutableListOf()
                responseLiveData.value = commentList
            }
        })

        return responseLiveData
    }
}