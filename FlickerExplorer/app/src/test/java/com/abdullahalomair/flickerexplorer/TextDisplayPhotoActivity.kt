package com.abdullahalomair.flickerexplorer

import android.os.Build
import com.abdullahalomair.flickerexplorer.controller.DisplayPhotoActivity
import com.abdullahalomair.flickerexplorer.controller.FlickrApplication
import com.abdullahalomair.flickerexplorer.controller.MainActivity
import org.junit.Before
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
        val controller =  Robolectric.buildActivity(DisplayPhotoActivity::class.java)
        displayPhotoActivity = controller
            .create()
            .get()
    }
}