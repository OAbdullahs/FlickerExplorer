package com.abdullahalomair.flickerexplorer.controller

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.abdullahalomair.flickerexplorer.*
import com.abdullahalomair.flickerexplorer.model.Photo
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*
import java.util.concurrent.ExecutionException


class GoogleMapBottomSheet(
    private val photos: List<Photo> = emptyList()
) : BottomSheetDialogFragment(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    private lateinit var googleMapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var progressBar: ProgressBar
    private val scope = CoroutineScope(Dispatchers.IO)
    private val scopeDefault = CoroutineScope(Dispatchers.Default)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener {
            val bottomSheet = (it as BottomSheetDialog).findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED

            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }

        // Do something with your dialog like setContentView() or whatever
        return dialog
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.google_map_fragment, container, false)
        googleMapView = view.findViewById(R.id.google_map_view)
        progressBar = view.findViewById(R.id.progress_bar_google_map)
        googleMapView.onCreate(savedInstanceState)
        googleMapView.getMapAsync(this)
        googleMapView.visibility = View.GONE
        return view
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        progressBar.visibility = View.GONE
        googleMapView.visibility = View.VISIBLE
        if (p0 != null) {
            googleMap = p0
            googleMap.uiSettings.isMyLocationButtonEnabled = false
            googleMap.isMyLocationEnabled = true
            googleMap.setOnMarkerClickListener(this)
            for (photo in photos) {
                val lat = photo.latitude.toDouble()
                val long = photo.longitude.toDouble()
                scope.launch {
                    try {
                        val image: Bitmap = Glide.with(requireContext())
                            .asBitmap()
                            .load(photo.url)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .apply(RequestOptions().override(150, 150))
                            .submit()
                            .get()
                        withContext(Dispatchers.Main) {
                            val myLocation = LatLng(lat, long)
                            googleMap.addMarker(
                                MarkerOptions()
                                    .position(myLocation)
                                    .title(photo.title)
                                    .icon(
                                        BitmapDescriptorFactory.fromBitmap(image))
                            )
                        }
                    } catch (e: Exception) {

                    }
                }
            }
            try {
                val lat = photos[0].latitude.toDouble()
                val long = photos[0].longitude.toDouble()
                val imagePosition = LatLng(lat, long)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(imagePosition, 10f))
            }catch (e:IndexOutOfBoundsException){
                dismiss()
            }
        }

    }


    override fun onResume() {
        googleMapView.onResume()
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
        googleMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        googleMapView.onDestroy()
        scope.cancel()
        scopeDefault.cancel()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        googleMapView.onLowMemory()
    }
    private fun goToExpandedPhoto(lat:String,lon:String,url:String,id:String,title:String){
        val intent = Intent(requireActivity(),DisplayPhotoActivity::class.java)
        intent.putExtra(URL,url)
        intent.putExtra(PHOTO_ID,id)
        intent.putExtra(PHOTO_TITLE,title)
        intent.putExtra(LAT, lat)
        intent.putExtra(LON, lon)
        requireActivity().startActivity(intent)
    }
    override fun onMarkerClick(p0: Marker?): Boolean {
        val marker = p0?.position
        val lat = marker?.latitude
        val lon = marker?.longitude
        scopeDefault.launch{
            if (lat != null && lon !=null) {
                for (photo in photos){
                    val photoLat= photo.latitude.toDouble()
                    val photoLon = photo.longitude.toDouble()
                    if (lat == photoLat && lon == photoLon){
                        withContext(Dispatchers.Main){
                            goToExpandedPhoto(photo.latitude,
                                photo.longitude,
                                photo.url,
                                photo.id,
                                photo.title)
                        }
                    }
                }
            }
        }
        return true
    }
}