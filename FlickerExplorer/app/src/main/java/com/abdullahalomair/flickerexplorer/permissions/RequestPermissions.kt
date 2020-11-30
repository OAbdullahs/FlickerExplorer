package com.abdullahalomair.flickerexplorer.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat

// method to check for permissions
 fun checkPermissions(context: Context): Boolean {
    val coarseLocation =  ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val fineLocation  = ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission
            .ACCESS_FINE_LOCATION
    )

    return coarseLocation == PackageManager.PERMISSION_GRANTED
            &&
           fineLocation  == PackageManager.PERMISSION_GRANTED
}


// method to check
// if location is enabled
fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(
        Context.LOCATION_SERVICE
    ) as LocationManager
    return (locationManager
        .isProviderEnabled(
            LocationManager.GPS_PROVIDER
        )
            || locationManager
        .isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        ))
}


