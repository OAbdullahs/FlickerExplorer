package com.abdullahalomair.flickerexplorer.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
private const val API_KEY = "c54bb018c48949815da7cc3fb7463c73"
private const val API_KEY_NAME= "api_key"
private const val FORMAT_NAME = "format"
private const val FORMAT = "json"
private const val NO_JSON_CALL_BACK = "nojsoncallback"
private const val NO_JSON_VALUE = "1"
private const val PHOTO_ID = "photo_id"
private const val LAT = "lat"
private const val LON = "lon"
private const val RADIUS = "radius"
private const val RADIUS_VALUE = "32"
private const val RADIUS_UNITS = "radius_units"
private const val KM = "km"
private const val EXTRAS = "extras"
private const val URLS = "url_c"
interface FlickrApi {

    @GET("services/rest?method=flickr.interestingness.getList" +
            "&$API_KEY_NAME=$API_KEY&$FORMAT_NAME=$FORMAT" +
            "&$NO_JSON_CALL_BACK=$NO_JSON_VALUE&$EXTRAS=$URLS")
    fun fetchInterestingPhotos():Call<FlickrInterestingResponse>

    @GET("services/rest/?method=flickr.photos.search"+
            "&$API_KEY_NAME=$API_KEY&$FORMAT_NAME=$FORMAT" +
            "&$NO_JSON_CALL_BACK=$NO_JSON_VALUE&$EXTRAS=$URLS" +
            "&$RADIUS=$RADIUS_VALUE&$RADIUS_UNITS=$KM")
    fun fetchLocalPhotos(@Query(LAT) lat:String,@Query(LON) lon:String)
            :Call<FlickrLocalResponse>

    @GET("services/rest/?method=flickr.photos.comments.getList"+
            "&$API_KEY_NAME=$API_KEY&$FORMAT_NAME=$FORMAT" +
            "&$NO_JSON_CALL_BACK=$NO_JSON_VALUE")
    fun fetchPhotoComments(@Query(PHOTO_ID) photo_id:String)
            :Call<FlickrCommentsResponse>
}