package com.abdullahalomair.flickerexplorer.api.interceptors

import android.util.Log
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
private const val API_KEY = "c54bb018c48949815da7cc3fb7463c73"
private const val API_KEY_NAME= "api_key"
private const val FORMAT_NAME = "format"
private const val FORMAT = "json"
private const val NO_JSON_CALL_BACK = "nojsoncallback"
private const val NO_JSON_VALUE = "1"
private const val PHOTO_ID = "photo_id"
class CommentInterceptor(private val photoId:String):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val newUrl: HttpUrl = originalRequest.url().newBuilder()
            .addQueryParameter(API_KEY_NAME, API_KEY)
            .addQueryParameter(PHOTO_ID, photoId)
            .addQueryParameter(FORMAT_NAME,FORMAT )
            .addQueryParameter(NO_JSON_CALL_BACK, NO_JSON_VALUE )
            .build()
        val newRequest: Request = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}