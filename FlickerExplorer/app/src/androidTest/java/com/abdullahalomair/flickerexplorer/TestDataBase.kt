package com.abdullahalomair.flickerexplorer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abdullahalomair.flickerexplorer.database.FlickrDataBase
import com.abdullahalomair.flickerexplorer.database.LikedPhotoDB
import com.abdullahalomair.flickerexplorer.database.LikedPhotoDao
import org.hamcrest.core.IsEqual.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TestDataBase {
    private lateinit var userDao: LikedPhotoDao
    private lateinit var db: FlickrDataBase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FlickrDataBase::class.java).build()
        userDao = db.likedPhotoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val user: LikedPhotoDB = LikedPhotoDB(
            photo_id = "123"
        )
        userDao.addPhoto(user)
        val byName = userDao.getLikedPhoto("123")
        assertThat(byName.value, equalTo(user))
    }



}