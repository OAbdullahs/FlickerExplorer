package com.abdullahalomair.flickerexplorer

import android.content.Intent
import android.os.Build
import android.widget.TextView
import com.abdullahalomair.flickerexplorer.controller.DisplayPhotoActivity
import com.abdullahalomair.flickerexplorer.controller.FlickrApplication
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = FlickrApplication::class)
class TextDisplayPhotoActivity {
    private lateinit var displayPhotoActivity: DisplayPhotoActivity

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        val intent = Intent()
        intent.putExtra(URL, "url")
        intent.putExtra(PHOTO_ID, "123")
        intent.putExtra(PHOTO_TITLE, "Hello")
        val controller = Robolectric.buildActivity(DisplayPhotoActivity::class.java,intent)
        displayPhotoActivity = controller
            .create()
            .get()
    }
    @Test
    fun checkIfPhotoNameChangedWhenPassingAnArguments(){
       val photoTitle:TextView =  displayPhotoActivity.findViewById(R.id.photo_title)
        Assert.assertEquals(photoTitle.text, "Hello")

    }
    @Test
    fun checkIfPhotoUrlChangedWhenPassingAnArguments(){
        val photoTitle =  displayPhotoActivity.intent.getStringExtra(URL)
        Assert.assertEquals(photoTitle, "url")

    }
    @Test
    fun checkIfPhotoIdChangedWhenPassingAnArguments(){
        val photoTitle =  displayPhotoActivity.intent.getStringExtra(PHOTO_ID)
        Assert.assertEquals(photoTitle, "123")
    }
}