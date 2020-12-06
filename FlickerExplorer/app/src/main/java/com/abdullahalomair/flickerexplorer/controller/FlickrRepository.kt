package com.abdullahalomair.flickerexplorer.controller

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.abdullahalomair.flickerexplorer.api.webrequest.FlickrCommentsFetcher
import com.abdullahalomair.flickerexplorer.api.webrequest.FlickrInterestingFetcher
import com.abdullahalomair.flickerexplorer.api.webrequest.FlickrLocalFetcher
import com.abdullahalomair.flickerexplorer.database.FlickrDataBase
import com.abdullahalomair.flickerexplorer.database.LikedPhotoDB
import com.abdullahalomair.flickerexplorer.model.Comment
import com.abdullahalomair.flickerexplorer.model.GalleryItem
import com.abdullahalomair.flickerexplorer.model.Photo
import java.util.concurrent.Executors

private const val DATABASE_NAME = "likedPhoto-database"
class FlickrRepository private constructor(context: Context){
    private val database : FlickrDataBase = Room.databaseBuilder(
        context.applicationContext,
        FlickrDataBase::class.java,
        DATABASE_NAME
    ).build()
    private val tasksDao = database.likedPhotoDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun addPhotoToDB(likedPhotoDB: LikedPhotoDB) {
        executor.execute {
            tasksDao.addPhoto(likedPhotoDB)
        }
    }
    fun deletePhotoFromDB(likedPhotoDB: LikedPhotoDB) {
        executor.execute {
            tasksDao.deletePhoto(likedPhotoDB)
        }
    }
    fun getLikedPhoto(id:String): LiveData<LikedPhotoDB?> = tasksDao.getLikedPhoto(id)
    fun getLikedPhotos(): LiveData<List<LikedPhotoDB>> = tasksDao.getLikedPhotos()
    fun getLocalPhotos(lat:String, lon:String):LiveData<List<Photo>>{
        return FlickrLocalFetcher().fetchLocalPhotos(lat,lon)
    }
    fun getInterestingPhotos():LiveData<List<GalleryItem>> {
        return FlickrInterestingFetcher().fetchInterestingPhotos()
    }
    fun getPhotoComments(photoId:String): LiveData<List<Comment>>{
        return FlickrCommentsFetcher().fetchPhotoComments(photoId)
    }

    companion object {
        private var INSTANCE: FlickrRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = FlickrRepository(context)
            }
        }
        fun get(): FlickrRepository {
            return INSTANCE ?:
            throw IllegalStateException("FlickrRepository must be initialized")
        }
    }
}