package com.abdullahalomair.flickerexplorer

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.abdullahalomair.flickerexplorer.permissions.checkPermissions
import com.abdullahalomair.flickerexplorer.permissions.isLocationEnabled
import com.abdullahalomair.flickerexplorer.viewmodel.MainActivityViewModel
import com.google.android.gms.location.*


private const val PERMISSION_ID = 44
class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var mFusedLocationClient:FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       mFusedLocationClient = LocationServices
            .getFusedLocationProviderClient(this)
       mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        getLastLocation()

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
    // method to requestfor permissions
     fun requestPermissions() {
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
            updateLocation(
                mLastLocation.latitude.toString(), mLastLocation.longitude.toString()
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation(){
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
                            Toast.makeText(
                                this,
                                result.latitude.toString()
                                        + result.longitude,
                                Toast.LENGTH_SHORT
                            ).show()
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
        var mFusedLocationClient = LocationServices
            .getFusedLocationProviderClient(this)
        mFusedLocationClient
            .requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()
            )

    }

    private fun updateLocation(lat: String, long: String){
        Toast.makeText(this, long + lat, Toast.LENGTH_SHORT).show()
    }
}