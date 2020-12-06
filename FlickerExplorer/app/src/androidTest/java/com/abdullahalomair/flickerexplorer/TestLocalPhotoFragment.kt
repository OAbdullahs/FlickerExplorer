package com.abdullahalomair.flickerexplorer

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.abdullahalomair.flickerexplorer.controller.LocalPhotosFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestLocalPhotoFragment {

    @Test
     fun testEventFragment() {
        // The "fragmentArgs" argument is optional.
        val argument = Bundle()
        argument.putString(LAT,"_lat")
        argument.putString(LON,"_lon")
        val scenario = launchFragmentInContainer<LocalPhotosFragment>(argument)
        scenario.moveToState(Lifecycle.State.CREATED)



    }


}