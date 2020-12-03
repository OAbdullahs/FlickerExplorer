package com.abdullahalomair.flickerexplorer.controller

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.abdullahalomair.flickerexplorer.*
import com.abdullahalomair.flickerexplorer.R
import com.abdullahalomair.flickerexplorer.model.HomeButtons
import com.abdullahalomair.flickerexplorer.permissions.checkPermissions
import com.abdullahalomair.flickerexplorer.permissions.isLocationEnabled
import com.google.android.gms.location.*
import kotlin.math.exp


private const val PERMISSION_ID = 44

class MainActivity : AppCompatActivity() {
    private lateinit var mFusedLocationClient:FusedLocationProviderClient
    private lateinit var explorerSearchButton: ImageView
    private lateinit var localHomeButton: ImageView
    private lateinit var favoriteButton:  ImageView
    private lateinit var notFilledHome: Drawable
    private lateinit var filledHome: Drawable
    private lateinit var notFillFavorite: Drawable
    private lateinit var fillFavorite: Drawable


    private lateinit var latitude:String
    private lateinit var longitude:String



    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Home Images
          notFilledHome = this.getDrawable(R.drawable.ic_home_white_empty)!!
          filledHome = this.getDrawable(R.drawable.ic_home_white_filled)!!
          notFillFavorite = this.getDrawable(R.drawable.ic_favorite_border_white)!!
          fillFavorite = this.getDrawable(R.drawable.ic_favorite_white_filled)!!

        explorerSearchButton = findViewById(R.id.go_to_explorer_button)
        localHomeButton = findViewById(R.id.go_to_local_button)
        favoriteButton = findViewById(R.id.go_to_setting_button)
       mFusedLocationClient = LocationServices
            .getFusedLocationProviderClient(this)
        getLastLocation()


        //Go to explorer Images
        explorerSearchButton.setOnClickListener {
            changeHomePageImages(HomeButtons.EXPLORER)
                val newFragment = PhotoExplorerFragment.newInstance()
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_manager,newFragment)
                        .commit()

        }
        favoriteButton.setOnClickListener {
            changeHomePageImages(HomeButtons.FAVORITE)
            val newFragment = LikedPhotosFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_manager,newFragment)
                .commit()
        }

        //Go To Local Images
        localHomeButton.setOnClickListener {
            changeHomePageImages(HomeButtons.LOCAL)
            val argument = Bundle()
            argument.putString(LAT,latitude)
            argument.putString(LON,longitude)

            val newFragment = LocalPhotosFragment.newInstance().apply {
                arguments = argument
            }
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_manager,newFragment)
                    .commit()
        }



    }

    private fun changeHomePageImages(homeButtons: HomeButtons){
        when(homeButtons){
            HomeButtons.LOCAL ->{
                localHomeButton.setImageDrawable(filledHome)
                favoriteButton.setImageDrawable(notFillFavorite)
            }
            HomeButtons.FAVORITE ->{
                favoriteButton.setImageDrawable(fillFavorite)
                localHomeButton.setImageDrawable(notFilledHome)
                //Change actionBar title
                this.title = this.getText(R.string.explorer)
            }
            HomeButtons.EXPLORER ->{
                localHomeButton.setImageDrawable(notFilledHome)
                favoriteButton.setImageDrawable(notFillFavorite)
                //Change actionBar title
                this.title = this.getText(R.string.explorer)
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_ID){
            if (grantResults.isNotEmpty()
                && grantResults[0]
                == PackageManager.PERMISSION_GRANTED) {

               getLastLocation()
            }
        }
    }
    // method to request for permissions
     private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(
            locationResult: LocationResult
        ) {
            val mLastLocation: Location = locationResult.lastLocation

               mLastLocation.latitude.toString()
                mLastLocation.longitude.toString()
            latAndLon(mLastLocation.latitude.toString(),
                mLastLocation.longitude.toString())

        }
    }


    @SuppressLint("MissingPermission")
    fun getLastLocation() {

        if(checkPermissions(this)){
            if (isLocationEnabled(this)){
                mFusedLocationClient
                    .lastLocation
                    .addOnCompleteListener { location ->
                        val result = location.result
                        if (result == null){
                            requestNewLocationData()
                        }
                        else{
                            latAndLon(result.latitude.toString(),
                                result.longitude.toString())
                        }
                    }
            }
            else{
                Toast.makeText(
                    this, this.getString(R.string.turn_location),
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(
                    Settings.ACTION_LOCATION_SOURCE_SETTINGS
                )
                startActivity(intent)
            }
        }
        else{
            requestPermissions()
        }
    }
    private fun latAndLon(_lat:String,_lon:String) {

        val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_manager)

        if (currentFragment == null){
            val argument = Bundle()
            argument.putString(LAT,_lat)
            argument.putString(LON,_lon)
            latitude = _lat
            longitude = _lon
            val toDoListMain = LocalPhotosFragment.newInstance().apply {
                arguments = argument
            }
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_manager,toDoListMain)
                    .commit()
        }


    }
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 5
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        // setting LocationRequest
        // on FusedLocationClient
        val mFusedLocationClient = LocationServices
            .getFusedLocationProviderClient(this)
        mFusedLocationClient
            .requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()
            )

    }
}