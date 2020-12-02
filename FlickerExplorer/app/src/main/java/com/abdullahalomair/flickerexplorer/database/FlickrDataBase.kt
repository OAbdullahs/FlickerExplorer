package com.abdullahalomair.flickerexplorer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [LikedPhotoDB::class],version = 1)
@TypeConverters(FlickrTypeConverter::class)
abstract class FlickrDataBase : RoomDatabase() {
    abstract fun likedPhotoDao(): LikedPhotoDao
}