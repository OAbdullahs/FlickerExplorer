package com.abdullahalomair.flickerexplorer.api.webrequest

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdullahalomair.flickerexplorer.api.*
import com.abdullahalomair.flickerexplorer.api.exception.WrongUrlException
import com.abdullahalomair.flickerexplorer.api.interceptors.CommentInterceptor
import com.abdullahalomair.flickerexplorer.model.Comment
import com.abdullahalomair.flickerexplorer.model.GalleryItem
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "FlickrCommentsFetcher"
class FlickrCommentsFetcher(private val photoId:String) {
    private val flickrApi: FlickrApi

    init {
        val client = OkHttpClient.Builder()
            .addInterceptor(CommentInterceptor(photoId))
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }
    private fun fetchPhotosRequest(): Call<FlickrCommentsResponse> {
        return flickrApi.fetchPhotoComments()
    }
    fun fetchPhotoComments(): LiveData<List<Comment>> {
        return fetchPhotoMetadata(fetchPhotosRequest())
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
                    Log.i(TAG,commentList.toString())
                responseLiveData.value = commentList
            }
        })

        return responseLiveData
    }
}