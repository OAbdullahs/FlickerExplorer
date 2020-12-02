package com.abdullahalomair.flickerexplorer.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface LikedPhotoDao {
    @Query("SELECT * FROM LikedPhotoDB")
    fun getLikedPhotos(): LiveData<List<LikedPhotoDB>>

    @Query("SELECT * FROM LikedPhotoDB WHERE photo_id=(:photo_id)")
    fun getLikedPhoto(photo_id: String): LiveData<LikedPhotoDB?>

    @Insert
    fun addPhoto(photo: LikedPhotoDB)
    @Delete
    fun deletePhoto(photo: LikedPhotoDB)
}