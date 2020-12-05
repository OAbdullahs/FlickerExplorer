package com.abdullahalomair.flickerexplorer


import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import com.abdullahalomair.flickerexplorer.controller.*
import junit.framework.TestCase.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config



@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = FlickrApplication::class)
class TestMainActivity {
    private lateinit var mainActivity: MainActivity

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    val controller =  Robolectric.buildActivity(MainActivity::class.java)
        mainActivity = controller
            .create()
            .get()
    }

    @Test
    @Throws(Exception::class)
    fun shouldNotBeNull() {
        assertNotNull(mainActivity)
    }
    @Test
    @Throws(Exception::class)
    fun shouldHaveLocalPhotosFragment(){
        assertNull(
            mainActivity.supportFragmentManager.findFragmentById(R.id.fragment_manager)
        )
    }
    @Test
    @Throws(Exception::class)
    fun buttonClickShouldChangeApplicationTitle(){
        val explorerButton: ImageView = mainActivity.findViewById(R.id.go_to_explorer_button)
        val didButtonClicked = explorerButton.performClick()
        assert(didButtonClicked)
        val expectedTitle = mainActivity.getText(R.string.explorer)

        assertEquals(mainActivity.title,expectedTitle)

    }
    @Test
    fun checkIfFragmentCanBeLunched(){
        mainActivity.getLastLocation()
        val newFragment = PhotoExplorerFragment.newInstance()
        mainActivity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_manager,newFragment)
            .commit()

        val fragment = mainActivity.supportFragmentManager.findFragmentById(R.id.fragment_manager)
        assertNotNull(fragment)

    }
    @Test
    fun checkIfTheClickedButtonNavigateToTheFragment(){
        val explorerButton:ImageView = mainActivity.findViewById(R.id.go_to_local_button)
        explorerButton.performClick()
        val fragment = mainActivity.supportFragmentManager.findFragmentById(R.id.fragment_manager)
        assertNotNull(fragment)
    }
    @Test
    fun checkIfValuePassedAreTheSameAsExpected(){
        val argument = Bundle()
        argument.putString(LAT, "24.7136")
        argument.putString(LON, "46.6753")
        val explorerButton:ImageView = mainActivity.findViewById(R.id.go_to_local_button)
        explorerButton.performClick()
        val fragment = mainActivity.supportFragmentManager.findFragmentById(R.id.fragment_manager)
            ?.apply {
                arguments = argument
            }
        if (fragment != null){
            val args = fragment.arguments
            val lat =  args?.getString(LAT, "")
            val lon = args?.getString(LON, "")

            assertEquals(lat,"24.7136")
            assertEquals(lon,"46.6753")
        }
    }

}

